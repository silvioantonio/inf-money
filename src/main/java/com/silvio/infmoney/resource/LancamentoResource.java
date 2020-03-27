package com.silvio.infmoney.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silvio.infmoney.model.Lancamento;
import com.silvio.infmoney.service.AbstractRestService;
import com.silvio.infmoney.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource extends AbstractRestController<Long, Lancamento>{

	@Autowired
	private LancamentoService lancamentoService;
	
	@Override
	public AbstractRestService<Lancamento, Long> getService() {
		return this.lancamentoService;
	}
	
	
}
