package com.projeto.luispaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.ItemPacote;

@Transactional(readOnly = true)
public interface ItemPacoteRepository  extends JpaRepository<ItemPacote, Long>, JpaSpecificationExecutor<ItemPacote>{

	@Query("SELECT i FROM ItemPacote i where i.codigoPacote = :codigoPacote")
	List<ItemPacote> buscarItensPorPacote(Long codigoPacote);
}
