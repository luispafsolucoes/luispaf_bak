package com.projeto.luispaf.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.PacoteVendido;

@Transactional(readOnly = true)
public interface PacoteVendidoRepositoty  extends  JpaRepository<PacoteVendido, Long>, JpaSpecificationExecutor<PacoteVendido> {

	 @Query(nativeQuery = true,
			 value = "SELECT count(*) as qtdepacote FROM pacotevendido pv\r\n"					
					+ "WHERE date(pv.datinicio) >= date(:dataInicio) "
					+ " and date(pv.datinicio) <= date(:dataFim)"
			 )
	 Long getTotalPacoteVendidoPorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(p.valor), 0)  FROM pacotevendido pv\r\n"
			 		+ "  inner join pacote p on p.codpacote = pv.codpacote\r\n"
			 		+ " where pv.status in('ATIVO','FINALIZADO') \r\n"			 		
					+ " and date(pv.datinicio) >= date(:dataInicio) "
					+ " and date(pv.datinicio) <= date(:dataFim)"			 		
			 		+ "and (:codigoCliente is null or pv.codcliente = :codigoCliente)"		 
			 )
	 Double getValorPacotePorClienteEPeriodo(@Param("dataInicio") Date dataInicio, 
			 								 @Param("dataFim") Date dataFim,
			 							     @Param("codigoCliente") Long codigoCliente);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT count(*)  FROM pacotevendido pv\r\n"
			 		+ "  inner join pacote p on p.codpacote = pv.codpacote\r\n"
			 		+ " where pv.status in('ATIVO','FINALIZADO') \r\n"			 		
					+ " and date(pv.datinicio) >= date(:dataInicio) "
					+ " and date(pv.datinicio) <= date(:dataFim)"			 		
			 		+ "and (:codigoCliente is null or pv.codcliente = :codigoCliente)"		 
			 )
	 Long getQtdePacotePorClienteEPeriodo(@Param("dataInicio") Date dataInicio, 
			 								 @Param("dataFim") Date dataFim,
			 							     @Param("codigoCliente") Long codigoCliente);
}
