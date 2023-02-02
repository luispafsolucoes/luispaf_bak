package com.projeto.luispaf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.Pacote;
import com.projeto.luispaf.repository.PacoteRepository;
import com.projeto.luispaf.repository.ProdutoRepository;
import com.projeto.luispaf.service.PacoteService;

@Service
public class PacoteServiceImpl implements PacoteService {
	
	@Autowired
	PacoteRepository repository;
	
	@Autowired
	ProdutoRepository produtoRepository;

	public Pacote salvar(Pacote entity) throws Exception {
		return repository.save(entity);
	}

	public void deletar(Long codigo) throws Exception {
		repository.deleteById(codigo);			
	}

	public List<Pacote> filtrar(Pacote entity) throws Exception {
		try {	
			Example<Pacote> entityEx = Example.of(entity,
					ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
							.withMatcher("nome", GenericPropertyMatchers.contains()));
			
			return repository.findAll(entityEx);		
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar pacote: " + e.getMessage());
		}
	}

	public boolean pacoteJaExiste(Pacote entity) throws Exception {
		List<Pacote> lista = repository.buscarPacote(entity.getNome());		
		if (!lista.isEmpty() && lista.size() > 0) {
			return true;
		}	
		return false;
	}

	public Page<Pacote> filtrarPage(Pacote entity, Pageable pageable) throws Exception {
		try {				
			ExampleMatcher matcher = ExampleMatcher
					.matching()
					.withIgnoreNullValues()
					.withIgnoreCase()
					.withMatcher("nome", GenericPropertyMatchers.contains());
			Example<Pacote> example = Example.of(entity, matcher);
			
			return repository.findAll(example, pageable);	
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar Pacote(page): " + e.getMessage());
		}
	}

	public void validarValorPacote(Pacote entity) throws Exception {
		if (entity.getValor() <= 0) {
			throw new Exception("O valor do Pacote tem que ser maior que 0");
		}		
	}
		
	public void getItensPacote(List<Pacote> lista) {
		lista.forEach((Pacote pc) -> {
			pc.setProdutos(produtoRepository.buscarProdutoPacote(pc.getCodigo()));
		});
	}
	
	public List<Pacote> listarPacotesAtivosOrdenadoPornome() {
		return repository.listarPacotesAtivosOrdenadoPornome();
	}
}
