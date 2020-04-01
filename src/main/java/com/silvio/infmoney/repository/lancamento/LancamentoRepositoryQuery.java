package com.silvio.infmoney.repository.lancamento;

import java.util.List;

import com.silvio.infmoney.model.Lancamento;
import com.silvio.infmoney.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}
