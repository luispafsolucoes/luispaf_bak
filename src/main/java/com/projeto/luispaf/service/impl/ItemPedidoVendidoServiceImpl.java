package com.projeto.luispaf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.ItemPedidoVendido;
import com.projeto.luispaf.model.PedidoVendido;
import com.projeto.luispaf.repository.ItemPedidoVendidoRepository;

@Service
public class ItemPedidoVendidoServiceImpl {
	
	@Autowired
	ItemPedidoVendidoRepository repository;

	public void salvarItensPedidoVendido(PedidoVendido entity) {
		if (entity.getProdutos() != null) {
			entity.getProdutos().forEach(prod -> {
				ItemPedidoVendido item = new ItemPedidoVendido();
				item.setCodigoPedidoVendido(entity.getCodigo());
				item.setCodigoProduto(prod.getCodigo());
				repository.save(item);
			});
		}		
	}
	
	public Double getTotalItens(PedidoVendido pedidoVendido) {
		return repository.getTotalItens(pedidoVendido.getCodigo());
	}
	
	public Page<ItemPedidoVendido> filtrarPage(PedidoVendido entity, Pageable pageable) throws Exception {
		try {	
			ItemPedidoVendido ite = new ItemPedidoVendido();
			ite.setCodigoPedidoVendido(entity.getCodigo());
			
			ExampleMatcher matcher = ExampleMatcher
					.matching()
					.withIgnoreNullValues()
					.withIgnoreCase()
					.withMatcher("nome", GenericPropertyMatchers.contains());
			Example<ItemPedidoVendido> example = Example.of(ite, matcher);
			
			return repository.findAll(example, pageable);	
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar ItemPedidoVendido(page): " + e.getMessage());
		}
	}
	
	public ItemPedidoVendido salvar(ItemPedidoVendido entity) {
		return repository.save(entity);
	}
	
	public void deletar(Long codigo) throws Exception {
		repository.deleteById(codigo);		
	}
}
