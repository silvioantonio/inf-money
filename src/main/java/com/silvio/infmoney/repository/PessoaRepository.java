package com.silvio.infmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silvio.infmoney.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
