package com.projeto.luispaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.Cidade;

@Transactional(readOnly = true)
public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade> {
	
	@Query("SELECT c FROM Cidade c where c.nome = :nome and c.uf = :uf")
	List<Cidade> buscarCidade(String nome, String uf);
	
	@Query("SELECT c FROM Cidade c order by c.nome")
	List<Cidade> listarCidadeOrdenadoPornome();
}
