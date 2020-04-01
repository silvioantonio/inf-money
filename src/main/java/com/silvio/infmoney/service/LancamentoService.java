package com.silvio.infmoney.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.silvio.infmoney.model.Lancamento;
import com.silvio.infmoney.model.Pessoa;
import com.silvio.infmoney.repository.LancamentoRepository;
import com.silvio.infmoney.repository.PessoaRepository;
import com.silvio.infmoney.repository.filter.LancamentoFilter;
import com.silvio.infmoney.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService extends AbstractRestService<Lancamento, Long>{
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaReposiotry;

	
	public Lancamento salvarLancamento(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaReposiotry.findById(lancamento.getPessoa().getId()); 
		if(pessoa.get() == null || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}
	
	public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
		return lancamentoRepository.filtrar(lancamentoFilter);
	}
	

	@Override
	public JpaRepository<Lancamento, Long> getRepository() {
		return this.lancamentoRepository;
	}

}
