package com.projeto.luispaf.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.PacoteVendido;
import com.projeto.luispaf.repository.PacoteVendidoRepositoty;

@Service
public class PacoteVendidoServiceImpl {
	
	@Autowired
	PacoteVendidoRepositoty repository;	


	public PacoteVendido salvar(PacoteVendido pacoteVendido) throws Exception {
		return repository.save(pacoteVendido);
	}
	
	public List<PacoteVendido> filtrar(PacoteVendido entity) throws Exception {
		try {	
			Example<PacoteVendido> entityEx = Example.of(entity,
					ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
							.withMatcher("status", GenericPropertyMatchers.contains()));
			
			List<PacoteVendido> lista =  repository.findAll(entityEx);				 
			return lista;			
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar ItemPacoteVendido: " + e.getMessage());
		}
	}
	
	public Page<PacoteVendido> filtrarPage(PacoteVendido entity, Pageable pageable) throws Exception {
		try {	
			
			ExampleMatcher matcher = ExampleMatcher
					.matching()
					.withIgnoreNullValues()
					.withIgnoreCase()
					.withMatcher("status", GenericPropertyMatchers.contains());
			Example<PacoteVendido> example = Example.of(entity, matcher);
			
			return repository.findAll(example, pageable);	
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar PacoteVendido(page): " + e.getMessage());
		}
	}	
	
	public Long getTotalPacoteVendidoPorPeriodo(Date dataInicio, Date DataFim) {
		return repository.getTotalPacoteVendidoPorPeriodo(dataInicio, DataFim);
	}
	
	public Double getValorPacotePorClienteEPeriodo(Date dataInicio, Date dataFim,Long codigoCliente) {
		return repository.getValorPacotePorClienteEPeriodo(dataInicio, dataFim, codigoCliente);
	}
	
	public  Long getQtdePacotePorClienteEPeriodo(Date dataInicio, Date dataFim,Long codigoCliente) {
		return repository.getQtdePacotePorClienteEPeriodo(dataInicio, dataFim, codigoCliente);
	}
}
	
