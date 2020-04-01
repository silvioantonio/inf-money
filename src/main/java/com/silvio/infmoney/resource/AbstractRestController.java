package com.silvio.infmoney.resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import javax.persistence.Id;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.silvio.infmoney.event.RecursoCriadoEvent;
import com.silvio.infmoney.service.AbstractRestService;


public abstract class AbstractRestController <ID, T> {
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<List<T>> listarTodos() {
		return ResponseEntity.ok(this.getService().getRepository().findAll());
	}
	
	@GetMapping("/{id}")
	public T buscaPorCodigo(@PathVariable ID id) {
		Optional<T> t;
		try {
			t = this.getService().getRepository().findById(id);
		} catch (IllegalArgumentException i) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Codigo não pode ser nulo!");
		}
		if(t.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "Codigo não existe");
		}
		return t.get();		
	}
	
	@PostMapping
	public ResponseEntity<T> salvar (@Valid @RequestBody T t, HttpServletResponse response)  {
		T t2 = null;
		t2 = this.getService().getRepository().save(t);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, (Long) getRowKey(t2)));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(t2);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable ID id) {
		this.getService().getRepository().deleteById(id);
	}
	
	/**
	 * Retorna um Objeto ID, que sera retirado de um objeto generico passado por parametro na assinatura do metodo,
	 * dessa forma é possivel passar qualquer model e retornar seu Id gerado apos salvar essa entidade no banco de dados.
	 * É necessario que se faça um casting para {@link Long} nesse contexto.
	 * @param Object - objeto generico 
	 * @return Object - id referente ao objeto passado por parametro
	 * @author silvio
	 * @exception IllegalArgumentException - Caso seja passado um objeto Nulo
	 */
	private Object getRowKey(Object obj) {
		Object id = null;
		Class<?> type = obj.getClass();

		for (Field f : type.getDeclaredFields()) {
			if (f.isAnnotationPresent((Class<? extends Annotation>) Id.class)) {
				String name = f.getName();
				String method = "get" + name.substring(0, 1).toUpperCase()
						+ name.substring(1);
				try {
					Method m = type.getDeclaredMethod(method);
					id = (Object) m.invoke(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return id;
	}

	
	public abstract AbstractRestService<T, ID> getService();

		
}
