package com.projeto.luispaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.luispaf.model.PedidoVendido;
import com.projeto.luispaf.repository.ClienteRepository;
import com.projeto.luispaf.repository.PedidoVendidoRepository;
import com.projeto.luispaf.service.PedidoVendidoService;
import com.projeto.luispaf.service.impl.CaixaServiceImpl;
import com.projeto.luispaf.service.impl.ClienteServiceImpl;
import com.projeto.luispaf.service.impl.ItemPedidoVendidoServiceImpl;
import com.projeto.luispaf.service.impl.PedidoVendidoServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidoVendido")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PedidoVendidoController {
	
	@Autowired
	PedidoVendidoService service;	
	@Autowired
	PedidoVendidoRepository	repository;	
	@Autowired
	ItemPedidoVendidoServiceImpl itemPedidoVendidoServiceImpl;	
	@Autowired
	ClienteServiceImpl clienteServiceImpl;	
	@Autowired
	ClienteRepository clienteRepository;	
	@Autowired
	PedidoVendidoServiceImpl pedidoVendidoServiceImpl;
	@Autowired
	CaixaServiceImpl caixaServiceImpl;
	
	@GetMapping("/todos")
	public ResponseEntity<?> todos(Pageable pageable) throws Exception{
		return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
	}
	
	@PostMapping("/filtrar")
	public ResponseEntity<?> filtrar(@RequestBody PedidoVendido entity) throws Exception {
	   try {
		  List<PedidoVendido> lista  = service.filtrar(entity);	
		  clienteServiceImpl.getClientePedidoVenda(lista);
		  return new ResponseEntity<List<PedidoVendido>>(lista, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/filtrarPage")
	public ResponseEntity<?> filtrarPage(@RequestBody PedidoVendido entity, Pageable pageable) throws Exception {
	   try {
		   Page<PedidoVendido> page  = service.filtrarPage(entity, pageable);		   
		   // Monta o total do pedido e Seta o cliente do pedido
		   clienteServiceImpl.getClientePedidoVenda(page.getContent());
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro(Page): " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salvar(@RequestBody PedidoVendido entity) throws Exception {	
		try {	
			Long qtdeCaixaAbertoDataAtual = caixaServiceImpl.getQtdeCaixaAbertoDataAtua();
			
			if (qtdeCaixaAbertoDataAtual > 0) {
				PedidoVendido pedidoVendidoSalvo = service.salvar(entity);
				itemPedidoVendidoServiceImpl.salvarItensPedidoVendido(pedidoVendidoSalvo);
				return new ResponseEntity<PedidoVendido>(pedidoVendidoSalvo, HttpStatus.OK);
			}
			
			// Só pode deixar gerar uma venda se o caixa já estiver aberto
			return new ResponseEntity<String>("Ainda não existe caixa aberto para a data atual!", HttpStatus.EXPECTATION_FAILED);				
		} catch (Exception e) {
			throw new Exception("Falha ao salvar pacote: " + e.getMessage());
		}		
	}

		
	@PostMapping("/listarPedidosDodia")
	public ResponseEntity<?> listarPedidosDodia(@RequestBody PedidoVendido entity, Pageable pageable) throws Exception {
	   try {
		   Page<PedidoVendido> page  = pedidoVendidoServiceImpl.listarClienteComPedidoAbertoNaDataAtual(entity.getCodigoCliente(), entity.getStatus(),entity.getDataCriacao(), pageable);				  
		   clienteServiceImpl.getClientePedidoVenda(page.getContent());
		   return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro(Page): " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/listarPedidosAberto")
	public ResponseEntity<?> listarPedidosAberto(Pageable pageable) throws Exception {
	   try {
		   Page<PedidoVendido> page  = pedidoVendidoServiceImpl.listarPedidosAberto(pageable);				  
		   clienteServiceImpl.getClientePedidoVenda(page.getContent());
		   return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro(Page): " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
}
