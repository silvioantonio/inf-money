package com.silvio.infmoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.silvio.infmoney.model.Lancamento;
import com.silvio.infmoney.repository.LancamentoRepository;

@Service
public class LancamentoService extends AbstractRestService<Lancamento, Long>{
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Override
	public JpaRepository<Lancamento, Long> getRepository() {
		return lancamentoRepository;
	}

}
