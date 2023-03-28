package com.projeto.luispaf.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.PedidoVendido;

@Transactional(readOnly = true)
public interface PedidoVendidoRepository  extends JpaRepository<PedidoVendido, Long>, JpaSpecificationExecutor<PedidoVendido>{

	@Query(nativeQuery = true,
			 value = "SELECT * FROM pedidovendido p\r\n"
			 		+ "  where p.status = :status  \r\n"
			 		+ " and  (DATE_FORMAT(:datCreate ,'%d-%m-%Y') is null or DATE_FORMAT(p.datcriacao ,'%d-%m-%Y') = DATE_FORMAT(:datCreate ,'%d-%m-%Y'))"
			 		+ " and  (:codigoCliente is null or p.codcliente = :codigoCliente)"	,
			 countQuery = "SELECT count(*) FROM pedidovendido p\r\n"
			 		+ "  where p.status = :status  \r\n"
			 		+ " and  (DATE_FORMAT(:datCreate ,'%d-%m-%Y') is null or DATE_FORMAT(p.datcriacao ,'%d-%m-%Y') = DATE_FORMAT(:datCreate ,'%d-%m-%Y'))"	
			 		+ " and  (:codigoCliente is null or p.codcliente = :codigoCliente)"	
			 )
	 Page<PedidoVendido> listarClienteComPedidoAbertoNaDataAtual(@Param("codigoCliente") Long codigoCliente, @Param("status") String status, @Param("datCreate") Date datCreate, Pageable pageable);

	@Query(nativeQuery = true,
			 value = "SELECT * FROM pedidovendido p where p.status = 'ABERTO'"	,
			 countQuery = "SELECT count(*) FROM pedidovendido p where p.status = 'ABERTO'"			 
			 )
	 Page<PedidoVendido> listarPedidosAberto(Pageable pageable);
	
	 @Query(nativeQuery = true,
			 value = "SELECT count(*) FROM pedidovendido p where p.status = 'ABERTO'")
	 Long getQtdePedidosAbertos();
	 
	 
	 @Query(nativeQuery = true,
			 value = "SELECT * FROM pedidovendido p where p.status = 'ABERTO'"		 
			 )
	 List<PedidoVendido> listarPedidosAberto();
	 
	 @Query(nativeQuery = true,
			 value = "SELECT count(*) FROM pedidovendido pv\r\n"
			 		+ "WHERE DATE_FORMAT(pv.datcriacao ,'%d-%m-%Y') >= DATE_FORMAT(:dataInicio ,'%d-%m-%Y') "
			 		+ " and DATE_FORMAT(pv.datcriacao ,'%d-%m-%Y') <= DATE_FORMAT(:dataFim ,'%d-%m-%Y')"					
			 )
	 Long getTotalProdutoVendidoPorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);

	 @Query(nativeQuery = true,
			 value = "SELECT  coalesce(sum(p.valor), 0) FROM pedidovendido pv\r\n"
			 		+ "  inner join itempedidovendido ipv on ipv.codpedidovendido = pv.codpedidovendido\r\n"
			 		+ "   inner join produto p on p.codproduto = ipv.codproduto\r\n"
			 		+ "WHERE pv.datcriacao BETWEEN :dataInicio AND :dataFim \r\n"
			 		+ "and pv.status in('RECEBIDO' )\r\n"
			 		+ " and (:codigoCliente is null or pv.codcliente = :codigoCliente)"		 
			 )
	 Double getValorPedidoPorClienteEPeriodo(@Param("dataInicio") Date dataInicio, 
			 								 @Param("dataFim") Date dataFim,
			 							     @Param("codigoCliente") Long codigoCliente);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT  count(*) FROM pedidovendido pv\r\n"
			 		+ "  inner join itempedidovendido ipv on ipv.codpedidovendido = pv.codpedidovendido\r\n"
			 		+ "   inner join produto p on p.codproduto = ipv.codproduto\r\n"
			 		+ "WHERE pv.datcriacao BETWEEN :dataInicio AND :dataFim \r\n"
			 		+ "and pv.status in('RECEBIDO' )\r\n"
			 		+ " and (:codigoCliente is null or pv.codcliente = :codigoCliente)"		 
			 )
	 Long getQtdePedidoPorClienteEPeriodo(@Param("dataInicio") Date dataInicio, 
			 								 @Param("dataFim") Date dataFim,
			 							     @Param("codigoCliente") Long codigoCliente);
}
