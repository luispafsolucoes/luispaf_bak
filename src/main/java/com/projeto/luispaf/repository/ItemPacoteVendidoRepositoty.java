package com.projeto.luispaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.ItemPacoteVendido;

@Transactional(readOnly = true)
public interface ItemPacoteVendidoRepositoty  extends  JpaRepository<ItemPacoteVendido, Long>, JpaSpecificationExecutor<ItemPacoteVendido> {

	@Query("SELECT i FROM ItemPacoteVendido i where i.codigoPacoteVendido = :codigoPacoteVendido")
	List<ItemPacoteVendido> buscarItensPacoteVendido(Long codigoPacoteVendido);
	
	@Query("SELECT count(*) FROM ItemPacoteVendido i where i.codigoPacoteVendido = :codigoPacoteVendido"
			+ " and i.status = 'ATIVO'")
	Long getQteItensAtivos(Long codigoPacoteVendido);
}
