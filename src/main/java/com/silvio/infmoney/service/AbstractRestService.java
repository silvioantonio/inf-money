package com.silvio.infmoney.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractRestService<E, ID> {

	public E save(E e) throws IllegalAccessException {
		try {
			return this.getRepository().save(e);
		} catch (IllegalArgumentException e1) {
			throw new IllegalAccessException(e1.toString());
		}
		
	}
	
	public E getById(ID id) {
		return this.getRepository().getOne(id);
	}
	
	public List<E> get(){
		return this.getRepository().findAll();
	}
	
	public abstract JpaRepository<E, ID> getRepository();
	
}
