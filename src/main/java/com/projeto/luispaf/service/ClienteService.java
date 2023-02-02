package com.projeto.luispaf.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.luispaf.model.Cliente;

public interface ClienteService {	
	public Cliente salvar(Cliente cliente) throws Exception;
	public void deletar(Long codigo) throws Exception;
	public List<Cliente> todos() throws Exception;
	public List<Cliente> filtrar(Cliente cliente) throws Exception;
	public boolean cpfCadastrado(String cpf) throws Exception;
	public boolean cpfUtilizadoParaOutroCliente(Cliente cliente) throws Exception;
	public Page<Cliente> filtrarPage(Cliente entity,  Pageable pageable) throws Exception;
}
