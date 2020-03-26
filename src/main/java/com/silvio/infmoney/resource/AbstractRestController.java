package com.silvio.infmoney.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.silvio.infmoney.service.AbstractRestService;


public abstract class AbstractRestController <ID, T> {

	@GetMapping
	public ResponseEntity<List<T>> listarTodos() {
		return ResponseEntity.ok(this.getService().getRepository().findAll());
	}
	
	@GetMapping("/{codigo}")
	public T buscaPorCodigo(@PathVariable ID id) {
		Optional<T> t;
		try {
			t = this.getService().getRepository().findById(id);
		} catch (IllegalArgumentException i) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Codigo de Lancamento não pode ser nulo!");
		}
		if(t.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "Codigo de Lancamento não existe");
		}
		return t.get();		
	}
	
	public abstract AbstractRestService<T, ID> getService();
	
}
