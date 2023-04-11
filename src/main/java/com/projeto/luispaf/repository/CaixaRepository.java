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
			 		+ " and  date(pv.datinicio) = date(:dataAtual)"		 
			 )
	 Double getTotalPacoteDoDia(@Param("dataAtual") Date dataAtual);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(p.valor), 0) FROM pedidovendido pv\r\n"
			 		+ "  inner join itempedidovendido ipv on ipv.codpedidovendido = pv.codpedidovendido\r\n"
			 		+ "  inner join produto p on p.codproduto = ipv.codproduto\r\n"
			 		+ " and pv.status = 'RECEBIDO' \r\n"
			 		+ " and  date(pv.datcriacao) = date(:dataAtual)"		 
			 )
	 Double getTotalprodutoDoDia(@Param("dataAtual") Date dataAtual);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT count(*) FROM caixa c \r\n"
			 		+ "  where  date(c.databertura) = date(:dataAtual)"		 
			 )
	 Long getQtdeCaixaCriadoDataAtual(@Param("dataAtual") Date dataAtual);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT count(*) FROM caixa c \r\n"
			 		+ "  where  date(c.databertura) != date(:dataAtual)\r\n"
			 		+ " and c.status = 'ABERTO'"		 
			 )
	 Long getQtdeCaixaAbertoOutrasDatas(@Param("dataAtual") Date dataAtual);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT count(*) FROM caixa c \r\n"
			 		+ "  where  date(c.databertura) = date(:dataAtual)\r\n"
			 		+ " and c.status = 'ABERTO'"		 
			 )
	 Long getQtdeCaixaAbertoDataAtua(@Param("dataAtual") Date dataAtual);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT * FROM caixa c \r\n"
			 		+ "  where  date(c.databertura) != date(:dataAbertura)\r\n"
			 		+ " and c.status = 'ABERTO'"		 
			 )
	 List<Caixa> getCaixasBertoDatasAnteriores(@Param("dataAbertura") Date dataAbertura);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(p.valor), 0) FROM pacotevendido pv\r\n"
			 		+ "  inner join pacote p on p.codpacote = pv.codpacote\r\n"
			 		+ " and pv.status in('ATIVO', 'FINALIZADO')\r\n"
			 		+ " and  date(pv.datinicio) = date(:dataCaixa)"		 
			 )
	 Double getTotalPacote(@Param("dataCaixa") Date dataCaixa);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(p.valor), 0) FROM pedidovendido pv\r\n"
			 		+ "  inner join itempedidovendido ipv on ipv.codpedidovendido = pv.codpedidovendido\r\n"
			 		+ "  inner join produto p on p.codproduto = ipv.codproduto\r\n"
			 		+ " and pv.status = 'RECEBIDO' \r\n"	
			 		+ " and date(pv.datcriacao) = date(:dataCaixa)"
			 )
	 Double getTotalproduto(@Param("dataCaixa") Date dataCaixa);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT * FROM caixa c \r\n"
			 		+ "  where  date(c.databertura) = date(:dataAbertura)"		 
			 )
	 Caixa getCaixaAbertoDoDia(@Param("dataAbertura") Date dataAbertura);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(c.totalproduto), 0) as totalproduto \r\n"
			 		+ "   FROM caixa c \r\n"					
					+ "WHERE date(c.databertura) >= date(:dataInicio) "
					+ " and date(c.databertura) <= date(:dataFim)"
			 )
	 Double getTotalprodutoPorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(c.totalpacote), 0) as totalproduto \r\n"
			 		+ "   FROM caixa c \r\n"			 					 		
					+ "WHERE date(c.databertura) >= date(:dataInicio) "
					+ " and date(c.databertura) <= date(:dataFim)"
			 )
	 Double getTotalpacotePorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT coalesce(sum(c.totalcaixa), 0) as totalproduto \r\n"
			 		+ "   FROM caixa c \r\n"			 							
					+ "WHERE date(c.databertura) >= date(:dataInicio) "
					+ " and date(c.databertura) <= date(:dataFim)"
			 )
	 Double getTotalcaixaPorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);
	 
	 @Query(nativeQuery = true,
			 value = "SELECT NOW()"		 
			 )
	 Date getDataAtual();
}
