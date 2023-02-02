package com.projeto.luispaf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.ItemPacote;
import com.projeto.luispaf.model.Pacote;
import com.projeto.luispaf.repository.ItemPacoteRepository;
import com.projeto.luispaf.service.ItemPacoteService;

@Service
public class ItemPacoteServiceImpl implements ItemPacoteService{
	
	@Autowired
	ItemPacoteRepository repository;	

	public void salvar(Pacote pacote) throws Exception {
		try {			
			pacote.getProdutos().forEach(produto -> {
				ItemPacote itemPacote = new ItemPacote();
				itemPacote.setCodigoPacote(pacote.getCodigo());
				itemPacote.setCodigoProduto(produto.getCodigo());
				repository.save(itemPacote);
			});
		} catch (Exception e) {
			throw new Exception("Falha ao salvar itens do pacote: " + e.getMessage());
		}		
	}

	public List<ItemPacote> buscarItensPorPacote(Long codigoPacote) throws Exception {
		return repository.buscarItensPorPacote(codigoPacote);
	}
	
	public Page<ItemPacote> filtrarPage(Pacote entity, Pageable pageable) throws Exception {
		try {	
			ItemPacote ite = new ItemPacote();
			ite.setCodigoPacote(entity.getCodigo());
			
			ExampleMatcher matcher = ExampleMatcher
					.matching()
					.withIgnoreNullValues()
					.withIgnoreCase()
					.withMatcher("nome", GenericPropertyMatchers.contains());
			Example<ItemPacote> example = Example.of(ite, matcher);
			
			return repository.findAll(example, pageable);	
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar ItemPacote(page): " + e.getMessage());
		}
	}	
}
