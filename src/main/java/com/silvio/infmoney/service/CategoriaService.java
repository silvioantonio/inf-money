package com.silvio.infmoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.silvio.infmoney.model.Categoria;
import com.silvio.infmoney.repository.CategoriaRepository;

@Service
public class CategoriaService extends AbstractRestService<Categoria, Long> {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public JpaRepository<Categoria, Long> getRepository() {
		return this.categoriaRepository;
	}

}
