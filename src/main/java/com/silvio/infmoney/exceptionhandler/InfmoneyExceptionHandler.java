package com.silvio.infmoney.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Classe responsavel por gerar menssagem para erros de leitura na entrada de dados do codigo.
 * Internamente possui uma classe {@link Erro} que permite adicionar as menssagens.
 * @version 1.0
 * @author silvio
 * 
 */
@ControllerAdvice
public class InfmoneyExceptionHandler extends ResponseEntityExceptionHandler {
	 
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String menssagemUsuario = messageSource.getMessage("menssagem.invalida", null, LocaleContextHolder.getLocale());
		String menssagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

		List<Erro> erros = Arrays.asList(new Erro(menssagemUsuario, menssagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	
	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		
		for (FieldError erro : bindingResult.getFieldErrors()) {
			String menssagemUsuario = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
			String menssagemDesenvolvedor = erro.toString();
			erros.add(new Erro(menssagemUsuario, menssagemDesenvolvedor));
		}
		
		return erros;
	}
	
	/**
	 * Metodo que trata de excetpions do tipo vazio
	 * caso o usuario tente acessar um recurso que nao exista.
	 * @author silvio
	 * @throws EmptyResultDataAccessException
	 * 
	 */
	@ExceptionHandler({EmptyResultDataAccessException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleEmptyResultDataAccessException() {}
	
	
	/**
	 * Classe chamada internamente por {@link InfmoneyExceptionHandler} para retornar as mensagens para usuario e desenvolvedor
	 * @author silvio
	 *
	 */
	public static class Erro{
		
		private String menssagemUsuario;
		private String menssagemDesenvolvedor;
		
		public Erro(String menssagemUsuario, String menssagemDesenvolvedor) {
			this.menssagemUsuario = menssagemUsuario;
			this.menssagemDesenvolvedor = menssagemDesenvolvedor;
		}

		public String getMenssagemUsuario() {
			return menssagemUsuario;
		}

		public String getMenssagemDesenvolvedor() {
			return menssagemDesenvolvedor;
		}
		
		
	}
	
}
