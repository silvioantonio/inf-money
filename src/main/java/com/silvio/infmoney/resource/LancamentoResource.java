package com.silvio.infmoney.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.silvio.infmoney.model.Lancamento;
import com.silvio.infmoney.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Lancamento> listarTodos() {
		return lancamentoRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public Lancamento buscaPorCodigo(@PathVariable Long codigo) {
		Optional<Lancamento> lancamento;
		try {
			lancamento = lancamentoRepository.findById(codigo);
		} catch (IllegalArgumentException i) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Codigo de Lancamento não pode ser nulo!");
		}
		if(lancamento.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "Codigo de Lancamento não existe");
		}
		return lancamento.get();
		
	}
	
}
