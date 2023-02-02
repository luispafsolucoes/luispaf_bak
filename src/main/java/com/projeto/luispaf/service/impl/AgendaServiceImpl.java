package com.projeto.luispaf.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.Agenda;
import com.projeto.luispaf.repository.AgendaRepository;

@Service
public class AgendaServiceImpl {
	
	@Autowired
	AgendaRepository repository;
	
	public Agenda salvar(Agenda entity) throws Exception {
		return repository.save(entity);
	}

	public void deletar(Long codigo) throws Exception {
		repository.deleteById(codigo);		
	}
	
	public Long getQtdeAgendadaHora(Long codigoEmpresa,	String status, String horario, String tipoProduto, Date dataAgenda) {
		return repository.getQtdeAgendadaHora(codigoEmpresa, status, horario, tipoProduto, dataAgenda);
	}
	
	public Page<Agenda> filtrarPage(Agenda entity,  Pageable pageable) throws Exception {
		try {				
			ExampleMatcher matcher = ExampleMatcher
					.matching()
					.withIgnoreNullValues()
					.withIgnoreCase()
					.withMatcher("status", GenericPropertyMatchers.contains());
			Example<Agenda> example = Example.of(entity, matcher);
			
			return repository.findAll(example, pageable);	
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar Agenda(page): " + e.getMessage());
		}
	}
	
	public List<Agenda> getClientesAgendadosDiasAnteriores() {
		return repository.getClientesAgendadosDiasAnteriores();
	}
	
	public Page<Agenda> getAgendaPorData(@Param("dataAgendamento") Date dataAgendamento, Pageable pageable) {
		return repository.getAgendaPorData(dataAgendamento, pageable);
	}

}
