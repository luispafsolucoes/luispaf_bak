package com.projeto.luispaf.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.Cliente;


@Transactional(readOnly = true)
public interface ClienteRepository  extends  JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente>{

	 public List<Cliente> findAllByOrderByNomeAsc();   
	 
	 @Query("SELECT c FROM Cliente c where c.cpf = :cpf")
	 List<Cliente> listarClientePorCpf(String cpf);
	 
	 @Query("SELECT c FROM Cliente c where c.cpf = :cpf and c.codigo != :idCliente")
	 List<Cliente> cpfUtilizadoParaOutroCliente(Long idCliente, String cpf);
	 
	 @Query("SELECT c FROM Cliente c order by c.nome")
	 List<Cliente> listarClienteOrdenadoPornome();	
	 
	 @Query(nativeQuery = true,
			 value = "select * from cliente c\r\n"
			 		+ "  where c.codcliente in(select p.codcliente from pedidovendido p\r\n"
			 		+ "						  where 1=1\r\n"
			 		+ "						 and DATE_FORMAT (p.datcriacao ,'%d-%m-%Y') = DATE_FORMAT (current_date() ,'%d-%m-%Y')\r\n"
			 		+ "						 and p.datfinalizacao is null)\r\n"
			 		+ "order by c.nome",
			 countQuery = "select count(*) from cliente c\r\n"
			 		+ "  where c.codcliente in(select p.codcliente from pedidovendido p\r\n"
			 		+ "						  where 1=1\r\n"
			 		+ "						 and DATE_FORMAT (p.datcriacao ,'%d-%m-%Y') = DATE_FORMAT (current_date() ,'%d-%m-%Y')\r\n"
			 		+ "						 and p.datfinalizacao is null)"			 
			 )
	 Page<Cliente> listarClientePedidoAbertoDataAtual(Pageable pageable);
}
