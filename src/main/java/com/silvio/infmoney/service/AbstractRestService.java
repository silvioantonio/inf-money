package com.silvio.infmoney.service;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractRestService<E, ID> {

	/*public E savei(E e) throws IllegalAccessException {
		try {
			return this.getRepository().save(e);
		} catch (IllegalArgumentException e1) {
			throw new IllegalAccessException(e1.toString());
		}
		
	}
	
	public void delete(ID id) {
		this.getById(id);
		this.getRepository().deleteById(id);
	}
	
	public E getById(ID id) {
		return this.getRepository().getOne(id);
	}
	
	public List<E> get(){
		return this.getRepository().findAll();
	}*/
	
	public abstract JpaRepository<E, ID> getRepository();
	
}
