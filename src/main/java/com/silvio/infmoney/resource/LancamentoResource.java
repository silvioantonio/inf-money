package com.silvio.infmoney.resource;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silvio.infmoney.event.RecursoCriadoEvent;
import com.silvio.infmoney.exceptionhandler.InfmoneyExceptionHandler.Erro;
import com.silvio.infmoney.model.Lancamento;
import com.silvio.infmoney.repository.filter.LancamentoFilter;
import com.silvio.infmoney.service.AbstractRestService;
import com.silvio.infmoney.service.LancamentoService;
import com.silvio.infmoney.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource extends AbstractRestController<Long, Lancamento>{

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Lancamento> listarTodos(LancamentoFilter lancamentoFilter) {
		return lancamentoService.pesquisar(lancamentoFilter);
	}
	
	@Override
	@PostMapping
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento t, HttpServletResponse response) {
		
			Lancamento lancamento = lancamentoService.salvarLancamento(t);
			
			publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getCodigo()));
			
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
	}
	
	/**
	 * Metodo que gera exceçao personalizada para uma entrada invalida de parametro
	 * @param ex
	 * @return {@link Erro} - Lista de erros
	 */
	@ExceptionHandler(PessoaInexistenteOuInativaException.class)
	public ResponseEntity<Object> handlerPessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		
		String menssagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String menssagemDesenvolvedor = ex.toString();

		List<Erro> erros = Arrays.asList(new Erro(menssagemUsuario, menssagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}

	
	@Override
	public AbstractRestService<Lancamento, Long> getService() {
		return this.lancamentoService;
	}
	
	
}
