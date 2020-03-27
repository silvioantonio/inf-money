package com.silvio.infmoney.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silvio.infmoney.model.Categoria;
import com.silvio.infmoney.service.AbstractRestService;
import com.silvio.infmoney.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource extends AbstractRestController<Long, Categoria>{

	@Autowired
	private CategoriaService categoriaservice;
	
	@Override
	public AbstractRestService<Categoria, Long> getService() {
		return this.categoriaservice;
	}
	
}
