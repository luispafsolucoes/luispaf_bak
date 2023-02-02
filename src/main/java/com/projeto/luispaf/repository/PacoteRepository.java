package com.projeto.luispaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.Pacote;

@Transactional(readOnly = true)
public interface PacoteRepository  extends  JpaRepository<Pacote, Long>, JpaSpecificationExecutor<Pacote> {
	
	@Query("SELECT p FROM Pacote p where p.nome = :nome and p.status = 'ATIVO'")
	List<Pacote> buscarPacote(String nome);
	
	@Query("SELECT p FROM Pacote p where p.status = 'ATIVO' order by p.nome")
	 List<Pacote> listarPacotesAtivosOrdenadoPornome();	

}

