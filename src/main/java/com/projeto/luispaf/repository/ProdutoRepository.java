package com.projeto.luispaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.Produto;

@Transactional(readOnly = true)
public interface ProdutoRepository extends  JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto>{

	@Query("SELECT p FROM Produto p where p.nome = :nome and p.status = 'ATIVO'")
	List<Produto> buscarProduto(String nome);
	
	@Query("SELECT p FROM Produto p "
			+ "where p.codigo in (SELECT i.codigoProduto FROM ItemPacote i where i.codigoPacote = :codigoPacote)")
	List<Produto> buscarProdutoPacote(Long codigoPacote);
	
	@Query("SELECT p FROM Produto p where p.status = 'ATIVO' order by p.nome")
	 List<Produto> listarProdutoAtivoOrdenadoPornome();
}
