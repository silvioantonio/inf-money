package com.silvio.infmoney.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.silvio.infmoney.model.Pessoa;
import com.silvio.infmoney.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping
	public List<Pessoa> listarTodos () {
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Pessoa> buscarPeloCodigo (@PathVariable Long id) {
		Optional<Pessoa> pessoaOp = pessoaRepository.findById(id);
		if(pessoaOp.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "Id de pessoa não existe");
		return pessoaOp;
	} 
	
	@PostMapping
	public ResponseEntity<Pessoa> criar (@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		
		/**
		 * ServletUriComponentsBuilder == helper do spring
		 * fromCurrentRequestUri() == pega apartir da requisiçao atual(no caso "/pessoas")
		 * path("/{id}") == adicionar a URI o 'id'
		 * buildAndExpand(pessoaSalva.getCodigo()).toUri() == adiciona esse 'id' na URI
		 */
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(pessoaSalva.getId()).toUri();
		
		/**
		 * Setta o Header 'Location' com essa URI
		 * O 'Location' é usado para fazer redirect ou quando um novo recurso é criado!
		 */
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(pessoaSalva);
		
	}
	
}
