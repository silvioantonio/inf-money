package com.silvio.infmoney.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.silvio.infmoney.event.RecursoCriadoEvent;
import com.silvio.infmoney.model.Lancamento;
import com.silvio.infmoney.repository.LancamentoRepository;
import com.silvio.infmoney.service.AbstractRestService;
import com.silvio.infmoney.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource extends AbstractRestController<Long, Lancamento>{

	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	@PostMapping
	public ResponseEntity<Lancamento> criarLancamento (@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = null;
		try {
			lancamentoSalvo = lancamentoService.save(lancamento);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}


	@Override
	public AbstractRestService<Lancamento, Long> getService() {
		return this.lancamentoService;
	}
	
	
}
