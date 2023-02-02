package com.projeto.luispaf.service.impl;

import java.util.ArrayList;
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
import com.projeto.luispaf.model.ItemPedidoVendido;
import com.projeto.luispaf.model.Produto;
import com.projeto.luispaf.repository.ProdutoRepository;
import com.projeto.luispaf.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	ProdutoRepository repository;

	public Produto salvar(Produto entity) throws Exception {
		return repository.save(entity);
	}

	public void deletar(Long codigo) throws Exception {
		repository.deleteById(codigo);
	}

	public List<Produto> filtrar(Produto entity) throws Exception {
		try {	
			Example<Produto> entityEx = Example.of(entity,
					ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
							.withMatcher("nome", GenericPropertyMatchers.contains()));
			
			List<Produto> lista =  repository.findAll(entityEx);	
			List<Produto> listaRet = new ArrayList<Produto>();
			
			// ordena a lista para retorno lista1.stream() .sorted((p1, p2) ->
			lista.stream().sorted((p1, p2) -> p1.getNome().compareTo(p2.getNome()))
	        .forEach(p -> listaRet.add(p));
			 
			return listaRet;
			
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar produto: " + e.getMessage());
		}
	}

	public boolean produtoJaexiste(Produto entity) throws Exception {
		List<Produto> lista = repository.buscarProduto(entity.getNome());
		if (!lista.isEmpty() && lista.size() > 0) {
			return true;
		}
		return false;
	}

	public Page<Produto> filtrarPage(Produto entity, Pageable pageable) throws Exception {
		try {
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase()
					.withMatcher("nome", GenericPropertyMatchers.contains());
			Example<Produto> example = Example.of(entity, matcher);

			return repository.findAll(example, pageable);
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar produto(page): " + e.getMessage());
		}
	}

	public void validarValorProduto(Produto produto) throws Exception {
		if (produto.getValor() <= 0) {
			throw new Exception("O valor do produto tem que ser maior que 0");
		}
	}
	
	public List<Produto> buscarProdutoPacote(Long codigoPacote) {
		return repository.buscarProdutoPacote(codigoPacote);
	}
	
	public void getProdutoItemPacote(List<ItemPacote> lista) {
		lista.forEach((ItemPacote ite) -> {
			ite.setProduto(repository.findById(ite.getCodigoProduto()).get());
		});
	}
	
	public void getProdutoItemPedidoVendido(List<ItemPedidoVendido> lista) {
		lista.forEach((ItemPedidoVendido ite) -> {
			ite.setProduto(repository.findById(ite.getCodigoProduto()).get());
		});
	}

	public void getProdutoItemPacoteVendido(List<ItemPacoteVendido> lista) {
		lista.forEach((ItemPacoteVendido ite) -> {
			ite.setProduto(repository.findById(ite.getCodigoProduto()).get());
		});
	}
}
