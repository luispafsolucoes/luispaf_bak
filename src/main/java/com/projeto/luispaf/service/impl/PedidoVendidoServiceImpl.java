package com.projeto.luispaf.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.PedidoVendido;
import com.projeto.luispaf.repository.PedidoVendidoRepository;
import com.projeto.luispaf.service.PedidoVendidoService;

@Service
public class PedidoVendidoServiceImpl implements PedidoVendidoService{

	@Autowired
	PedidoVendidoRepository repository;
	
	@Autowired
	ClienteServiceImpl clienteServiceImpl;
	
	public PedidoVendido salvar(PedidoVendido entity) {
		return repository.save(entity);
	}

	public List<PedidoVendido> filtrar(PedidoVendido entity) throws Exception {
		try {	
			Example<PedidoVendido> entityEx = Example.of(entity,
					ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
							.withMatcher("codigo", GenericPropertyMatchers.contains()));
			
			List<PedidoVendido> lista =  repository.findAll(entityEx);			 
			return lista;			
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar PedidoVendido: " + e.getMessage());
		}
	}

	public Page<PedidoVendido> filtrarPage(PedidoVendido entity, Pageable pageable) throws Exception {
		try {
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase()
					.withMatcher("codigo", GenericPropertyMatchers.contains());
			Example<PedidoVendido> example = Example.of(entity, matcher);

			return repository.findAll(example, pageable);
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar PedidoVendido(page): " + e.getMessage());
		}
	}

	public void validarValorPedido(PedidoVendido entity) throws Exception {
		if (entity.getValor() <= 0) {
			throw new Exception("O valor do produto tem que ser maior que 0");
		}		
	}

	public List<PedidoVendido> buscarProdutoPedido(Long codigoPedido) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public Page<PedidoVendido> listarClienteComPedidoAbertoNaDataAtual(Long codigoCliente, String status, Pageable pageable) {
		return repository.listarClienteComPedidoAbertoNaDataAtual(codigoCliente, status, pageable);
	}
	
	public  Page<PedidoVendido> listarPedidosAberto(Pageable pageable) {
		return repository.listarPedidosAberto(pageable);
	}
	
	public Long getQtdePedidosAbertos() {
		return repository.getQtdePedidosAbertos();
	}
	
	public List<PedidoVendido> listarPedidosAberto() {
		return repository.listarPedidosAberto();
	}
	
	public Long getTotalProdutoVendidoPorPeriodo(Date dataInicio, Date DataFim) {
		return repository.getTotalProdutoVendidoPorPeriodo(dataInicio, DataFim);
	}
	
	public Double getValorPedidoPorClienteEPeriodo(Date dataInicio, Date dataFim, Long codigoCliente) {
		return repository.getValorPedidoPorClienteEPeriodo(dataInicio, dataFim, codigoCliente);
	}
	
	public Long getQtdePedidoPorClienteEPeriodo(Date dataInicio, Date dataFim, Long codigoCliente) {
		return repository.getQtdePedidoPorClienteEPeriodo(dataInicio, dataFim, codigoCliente);
	}
}
