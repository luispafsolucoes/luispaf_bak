package com.projeto.luispaf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.Agenda;
import com.projeto.luispaf.model.Cliente;
import com.projeto.luispaf.model.PacoteVendido;
import com.projeto.luispaf.model.PedidoVendido;
import com.projeto.luispaf.repository.ClienteRepository;
import com.projeto.luispaf.repository.PacoteRepository;
import com.projeto.luispaf.service.ClienteService;


@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	ClienteRepository repository;	
	@Autowired
	PacoteRepository pacoteRepository;
	
	@Autowired
	ItemPedidoVendidoServiceImpl itemPedidoVendidoServiceImpl;

	public Cliente salvar(Cliente entity) throws Exception {
		return repository.save(entity);
	}

	public void deletar(Long codigo) throws Exception {
		repository.deleteById(codigo);		
	}	
	
	public List<Cliente> todos() throws Exception {
		return repository.findAllByOrderByNomeAsc();
	}

	public List<Cliente> filtrar(Cliente entity) throws Exception {
		try {	
			Example<Cliente> entityEx = Example.of(entity,
					ExampleMatcher
					.matchingAll()
					.withIgnoreNullValues()
					.withIgnoreCase()
					.withMatcher("nome", GenericPropertyMatchers.contains()));
			
			return repository.findAll(entityEx);		
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar Cliente: " + e.getMessage());
		}
	}

	public boolean cpfCadastrado(String cpf) throws Exception {
		List<Cliente> lista =  repository.listarClientePorCpf(cpf);
		if (!lista.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean cpfUtilizadoParaOutroCliente(Cliente cliente) throws Exception {
		List<Cliente> lista =  repository.cpfUtilizadoParaOutroCliente(cliente.getCodigo(), cliente.getCpf());
		if (!lista.isEmpty()) {
			return true;
		}
		return false;
	}

	public Page<Cliente> filtrarPage(Cliente entity, Pageable pageable) throws Exception {
		try {				
			ExampleMatcher matcher = ExampleMatcher
					.matching()
					.withIgnoreNullValues()
					.withIgnoreCase()
					.withMatcher("nome", GenericPropertyMatchers.contains())
					.withMatcher("telefone1", GenericPropertyMatchers.contains())
					.withMatcher("cpf", GenericPropertyMatchers.contains());
			Example<Cliente> example = Example.of(entity, matcher);
			
			return repository.findAll(example, pageable);	
		} catch (Exception e) {
			throw new Exception("Falha ao filtrar cliente(page): " + e.getMessage());
		}
	}	
	
	public void getClientePedidoVenda(List<PedidoVendido> lista) {
		lista.forEach((PedidoVendido pedidoVendido) -> {
			// Monta o total do pedido
			pedidoVendido.setValor(itemPedidoVendidoServiceImpl.getTotalItens(pedidoVendido));
			// Seta o cliente do pedido
			pedidoVendido.setCliente(repository.findById(pedidoVendido.getCodigoCliente()).get());	
		});
	}
	
	public void getClientePacoteVendido(List<PacoteVendido> lista) {
		lista.forEach((PacoteVendido pacv) -> {
			// Seta o cliente
			pacv.setCliente(repository.findById(pacv.getCodigoCliente()).get());
			// Seta o pacote
			pacv.setPacote(pacoteRepository.findById(pacv.getCodigoPacote()).get());
		});
	}
	
	public void setCliente(List<Agenda> list) {
		list.forEach(ag -> {
			ag.setCliente(repository.findById(ag.getCodigoCliente()).get());
		});	
	}	
}
