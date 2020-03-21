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

import com.silvio.infmoney.model.Categoria;
import com.silvio.infmoney.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listarTodos () {
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public Optional<Categoria> buscarPeloCodigo (@PathVariable Long codigo) {
		Optional<Categoria> categoriaOp = categoriaRepository.findById(codigo);
		if(categoriaOp.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "Codigo de categoria não existe");
		return categoriaOp;
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar (@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		/**
		 * ServletUriComponentsBuilder == helper do spring
		 * fromCurrentRequestUri() == pega apartir da requisiçao atual(no caso "/categorias")
		 * path("/{codigo}") == adicionar a URI o 'codigo'
		 * buildAndExpand(categoriaSalva.getCodigo()).toUri() == adiciona esse 'codigo' na URI
		 */
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequestUri()
				.path("/{codigo}")
				.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		
		/**
		 * Setta o Header 'Location' com essa URI
		 * O 'Location' é usado para fazer redirect ou quando um novo recurso é criado!
		 */
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriaSalva);
		
	}
	
}
