package com.projeto.luispaf.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.Caixa;

@Transactional(readOnly = true)
public interface CaixaRepository  extends JpaRepository<Caixa, Long>, JpaSpecificationExecutor<Caixa>{

	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(p.valor), 0) FROM pacotevendido pv\r\n"
			 		+ "  inner join pacote p on p.codpacote = pv.codpacote\r\n"
			 		+ " and pv.status in('ATIVO', 'FINALIZADO')\r\n"
			 		+ " and  DATE_FORMAT(pv.datinicio ,'%d-%m-%Y') = DATE_FORMAT (current_date() ,'%d-%m-%Y')"		 
			 )
	 Double getTotalPacoteDoDia();
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(p.valor), 0) FROM pedidovendido pv\r\n"
			 		+ "  inner join itempedidovendido ipv on ipv.codpedidovendido = pv.codpedidovendido\r\n"
			 		+ "  inner join produto p on p.codproduto = ipv.codproduto\r\n"
			 		+ " and pv.status = 'RECEBIDO' \r\n"
			 		+ " and  DATE_FORMAT(pv.datcriacao ,'%d-%m-%Y') = DATE_FORMAT (current_date() ,'%d-%m-%Y')"		 
			 )
	 Double getTotalprodutoDoDia();
	 
	 @Query(nativeQuery = true,
			 value = "SELECT count(*) FROM caixa c \r\n"
			 		+ "  where  DATE_FORMAT(c.databertura ,'%d-%m-%Y') = DATE_FORMAT (current_date() ,'%d-%m-%Y')"		 
			 )
	 Long getQtdeCaixaCriadoDataAtual();
	 
	 @Query(nativeQuery = true,
			 value = "SELECT count(*) FROM caixa c \r\n"
			 		+ "  where  DATE_FORMAT(c.databertura ,'%d-%m-%Y') != DATE_FORMAT (current_date() ,'%d-%m-%Y')\r\n"
			 		+ " and c.status = 'ABERTO'"		 
			 )
	 Long getQtdeCaixaAbertoOutrasDatas();
	 
	 @Query(nativeQuery = true,
			 value = "SELECT count(*) FROM caixa c \r\n"
			 		+ "  where  DATE_FORMAT(c.databertura ,'%d-%m-%Y') = DATE_FORMAT (current_date() ,'%d-%m-%Y')\r\n"
			 		+ " and c.status = 'ABERTO'"		 
			 )
	 Long getQtdeCaixaAbertoDataAtua();
	 
	 @Query(nativeQuery = true,
			 value = "SELECT * FROM caixa c \r\n"
			 		+ "  where  DATE_FORMAT(c.databertura ,'%d-%m-%Y') != DATE_FORMAT (current_date() ,'%d-%m-%Y')\r\n"
			 		+ " and c.status = 'ABERTO'"		 
			 )
	 List<Caixa> getCaixasBertoDatasAnteriores();
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(p.valor), 0) FROM pacotevendido pv\r\n"
			 		+ "  inner join pacote p on p.codpacote = pv.codpacote\r\n"
			 		+ " and pv.status in('ATIVO', 'FINALIZADO')\r\n"
			 		+ " and  DATE_FORMAT(pv.datinicio ,'%d-%m-%Y') = DATE_FORMAT (:dataCaixa ,'%d-%m-%Y')"		 
			 )
	 Double getTotalPacote(@Param("dataCaixa") Date dataCaixa);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(p.valor), 0) FROM pedidovendido pv\r\n"
			 		+ "  inner join itempedidovendido ipv on ipv.codpedidovendido = pv.codpedidovendido\r\n"
			 		+ "  inner join produto p on p.codproduto = ipv.codproduto\r\n"
			 		+ " and pv.status = 'RECEBIDO' \r\n"
			 		+ " and  DATE_FORMAT(pv.datcriacao ,'%d-%m-%Y') = DATE_FORMAT (:dataCaixa ,'%d-%m-%Y')"		 
			 )
	 Double getTotalproduto(@Param("dataCaixa") Date dataCaixa);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT * FROM caixa c \r\n"
			 		+ "  where  DATE_FORMAT(c.databertura ,'%d-%m-%Y') = DATE_FORMAT (current_date() ,'%d-%m-%Y')"		 
			 )
	 Caixa getCaixaAbertoDoDia();
	 
	 @Query(nativeQuery = true,
			 value = "SELECT sum(c.totalproduto) as totalproduto \r\n"
			 		+ "   FROM caixa c \r\n"
			 		+ "  WHERE c.databertura BETWEEN :dataInicio AND :dataFim"		 
			 )
	 Double getTotalprodutoPorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT sum(c.totalpacote) as totalproduto \r\n"
			 		+ "   FROM caixa c \r\n"
			 		+ "  WHERE c.databertura BETWEEN :dataInicio AND :dataFim"		 
			 )
	 Double getTotalpacotePorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT sum(c.totalcaixa) as totalproduto \r\n"
			 		+ "   FROM caixa c \r\n"
			 		+ "  WHERE c.databertura BETWEEN :dataInicio AND :dataFim"		 
			 )
	 Double getTotalcaixaPorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT NOW()"		 
			 )
	 Date getDataAtual();
}
