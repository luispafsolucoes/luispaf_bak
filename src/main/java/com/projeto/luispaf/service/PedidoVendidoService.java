package com.projeto.luispaf.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.luispaf.model.PedidoVendido;

public interface PedidoVendidoService {

	public PedidoVendido salvar(PedidoVendido entity) throws Exception;
	public List<PedidoVendido> filtrar(PedidoVendido entity) throws Exception;
	public Page<PedidoVendido> filtrarPage(PedidoVendido entity,  Pageable pageable) throws Exception;
	public void validarValorPedido(PedidoVendido entity) throws Exception;
	public List<PedidoVendido> buscarProdutoPedido(Long codigoPedido);
}
