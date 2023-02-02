package com.projeto.luispaf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.Cidade;
import com.projeto.luispaf.model.Cliente;
import com.projeto.luispaf.repository.CidadeRepository;
import com.projeto.luispaf.service.CidadeService;

@Service
public class CidadeServiceImpl implements CidadeService{
	
	@Autowired
	CidadeRepository repository;

	public Cidade salvar(Cidade entity) throws Exception {
		return repository.save(entity);
	}

	public void deletar(Long codigo) throws Exception {
		repository.deleteById(codigo);		
	}

	public List<Cidade> filtrar(Cidade entity) throws Exception {
		try {	
			Example<Cidade> entityEx = Example.of(entity,
					ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
							.withMatcher("nome", GenericPropertyMatchers.contains()));
			
			return repository.findAll(entityEx);		
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar cidade: " + e.getMessage());
		}
	}
	
	public Page<Cidade> filtrarPage(Cidade entity,  Pageable pageable) throws Exception {
		try {				
			ExampleMatcher matcher = ExampleMatcher
					.matching()
					.withIgnoreNullValues()
					.withIgnoreCase()
					.withMatcher("nome", GenericPropertyMatchers.contains());
			Example<Cidade> example = Example.of(entity, matcher);
			
			return repository.findAll(example, pageable);	
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar clidade(page): " + e.getMessage());
		}
	}
			
	public boolean cidadeJaexiste(Cidade cidade) throws Exception {
		List<Cidade> lista = repository.buscarCidade(cidade.getNome(), cidade.getUf());		
		if (!lista.isEmpty() && lista.size() > 0) {
			return true;
		}
		return false;
	}	
	
	public void getCidadeCliente(List<Cliente> lista) {
		lista.forEach((Cliente cli) -> {
			cli.setCidade(repository.findById(cli.getCodigoCidade()).get());
		});
	}
}
