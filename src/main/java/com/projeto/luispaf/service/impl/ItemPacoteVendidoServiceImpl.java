package com.projeto.luispaf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.ItemPacote;
import com.projeto.luispaf.model.ItemPacoteVendido;
import com.projeto.luispaf.model.PacoteVendido;
import com.projeto.luispaf.repository.ItemPacoteVendidoRepositoty;
import com.projeto.luispaf.repository.PacoteVendidoRepositoty;

@Service
public class ItemPacoteVendidoServiceImpl {
	
	@Autowired
	ItemPacoteVendidoRepositoty repository;	
	@Autowired
	ItemPacoteServiceImpl itemPacoteServiceImpl;
	@Autowired
	PacoteVendidoRepositoty pacoteVendidoRepositoty;

	
	public void salvarItensPacote(PacoteVendido pacv) throws Exception {		
		try {			
			if (pacv.getCodigoPacote() != null) {
				 List<ItemPacote> listItens = itemPacoteServiceImpl.buscarItensPorPacote(pacv.getCodigoPacote());
				 listItens.forEach(item -> {
					 ItemPacoteVendido itePc = new ItemPacoteVendido();
					 itePc.setCodigoPacoteVendido(pacv.getCodigo());
					 itePc.setCodigoProduto(item.getCodigoProduto());
					 itePc.setDataInicio(new Date());
					 itePc.setStatus("ATIVO");
					 repository.save(itePc);
				 });
			}
		} catch (Exception e) {
			throw new Exception("Falha ao salvar itens do ItemPacoteVendido: " + e.getMessage());
		}		
	}
	
	public ItemPacoteVendido salvar(ItemPacoteVendido ite) throws Exception {		
		return repository.save(ite);		
	}
	
	public List<ItemPacoteVendido> filtrar(ItemPacoteVendido entity) throws Exception {
		try {	
			Example<ItemPacoteVendido> entityEx = Example.of(entity,
					ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
							.withMatcher("status", GenericPropertyMatchers.contains()));
			
			List<ItemPacoteVendido> lista =  repository.findAll(entityEx);				 
			return lista;			
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar ItemPacoteVendido: " + e.getMessage());
		}
	}

	
	public Page<ItemPacoteVendido> filtrarPage(ItemPacoteVendido entity, Pageable pageable) throws Exception {
		try {				
			ExampleMatcher matcher = ExampleMatcher
					.matching()
					.withIgnoreNullValues()
					.withIgnoreCase()
					.withMatcher("status", GenericPropertyMatchers.contains());
			Example<ItemPacoteVendido> example = Example.of(entity, matcher);
			
			return repository.findAll(example, pageable);	
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar ItemPacoteVendido(page): " + e.getMessage());
		}
	}
	
	public void cancelarItensPacoteVendido(PacoteVendido pacotev) {
		List<ItemPacoteVendido> lista = repository.buscarItensPacoteVendido(pacotev.getCodigo());
		lista.forEach(item -> {
			item.setDataFim(new Date());
			item.setStatus("CANCELADO");
			repository.save(item);
		});
	}
		
	public List<PacoteVendido> inativarItemPacoteVendido(ItemPacoteVendido entity) {
		List<PacoteVendido> lista = new ArrayList<>();
		repository.save(entity);
		Long qtdeAtivos = repository.getQteItensAtivos(entity.getCodigoPacoteVendido());
		
		PacoteVendido pcv = pacoteVendidoRepositoty.findById(entity.getCodigoPacoteVendido()).get();
		
		// Caso seja o ultimo item inativado, inativa o pai tambem
		if (qtdeAtivos == 0) {			
			pcv.setDataFim(new Date());
			pcv.setStatus("FINALIZADO");
			pcv = pacoteVendidoRepositoty.save(pcv);
		}
		lista.add(pcv);
		return lista;
	}
}
