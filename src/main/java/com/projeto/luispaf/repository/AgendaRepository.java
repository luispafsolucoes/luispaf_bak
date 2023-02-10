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

import com.projeto.luispaf.model.Agenda;

@Transactional(readOnly = true)
public interface AgendaRepository  extends JpaRepository<Agenda, Long>, JpaSpecificationExecutor<Agenda> {

	@Query(nativeQuery = true,
			 value = "SELECT count(*) FROM agenda a\r\n"
			 		+ "  where a.codempresa = :codigoEmpresa \r\n"
			 		+ " and a.status = :status \r\n"
			 		+ " and a.horario = :horario \r\n"
			 		+ " and a.tipoproduto = :tipoProduto \r\n"
			 		+ " and DATE_FORMAT(a.datagenda ,'%d-%m-%Y') = DATE_FORMAT (:dataAgenda ,'%d-%m-%Y')"		 
			 )
	 Long getQtdeAgendadaHora(
			 @Param("codigoEmpresa") Long codigoEmpresa,
			 @Param("status") String status,
			 @Param("horario") String horario,
			 @Param("tipoProduto") String tipoProduto,
			 @Param("dataAgenda") Date dataAgenda);
	
	@Query(nativeQuery = true,
			 value = "SELECT * FROM agenda a\r\n"
			 		+ "  where  DATE_FORMAT(a.datagenda ,'%d-%m-%Y') < DATE_FORMAT (current_date() ,'%d-%m-%Y')\r\n"
			 		+ " and a.status = 'ATIVO'"		 
			 )
	 List<Agenda> getClientesAgendadosDiasAnteriores();
	
	
	@Query(nativeQuery = true,
			 value = "SELECT * FROM agenda a where  DATE_FORMAT(a.datagenda ,'%d-%m-%Y') = DATE_FORMAT (:dataAgendamento ,'%d-%m-%Y')"	,
			 countQuery = "SELECT count(*) FROM agenda a where  DATE_FORMAT(a.datagenda ,'%d-%m-%Y') = DATE_FORMAT (:dataAgendamento ,'%d-%m-%Y')"			 
			 )
	 Page<Agenda> getAgendaPorData(@Param("dataAgendamento") Date dataAgendamento, Pageable pageable);
	
}
