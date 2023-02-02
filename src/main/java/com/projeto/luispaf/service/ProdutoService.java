package com.projeto.luispaf.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.luispaf.model.Produto;

public interface ProdutoService {
	public Produto salvar(Produto entity) throws Exception;
	public void deletar(Long codigo) throws Exception;
	public List<Produto> filtrar(Produto entity) throws Exception;
	public boolean produtoJaexiste(Produto entity) throws Exception;
	public Page<Produto> filtrarPage(Produto entity,  Pageable pageable) throws Exception;
	public void validarValorProduto(Produto produto) throws Exception;
	public List<Produto> buscarProdutoPacote(Long codigoPacote);
}
