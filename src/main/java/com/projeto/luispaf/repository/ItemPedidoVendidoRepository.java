package com.projeto.luispaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.ItemPedidoVendido;

@Transactional(readOnly = true)
public interface ItemPedidoVendidoRepository extends JpaRepository<ItemPedidoVendido, Long>, JpaSpecificationExecutor<ItemPedidoVendido>{

	@Query(nativeQuery = true,
			 value = "SELECT sum(pr.valor) FROM luispaf.itempedidovendido p\r\n"
			 		+ "  inner join luispaf.produto pr on pr.codproduto = p.codproduto\r\n"
			 		+ " and p.codpedidovendido = :codPedidoVendido")
	 Double getTotalItens(@Param("codPedidoVendido") Long codPedidoVendido);
}
