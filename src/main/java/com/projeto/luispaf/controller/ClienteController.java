package com.projeto.luispaf.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.luispaf.model.Cliente;
import com.projeto.luispaf.repository.ClienteRepository;
import com.projeto.luispaf.service.ClienteService;
import com.projeto.luispaf.service.impl.CidadeServiceImpl;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ClienteController {
	
	@Autowired
	ClienteService service;
	
	@Autowired
	ClienteRepository repository;
	@Autowired
	CidadeServiceImpl cidadeServiceImpl;
		
	/*
	 * retorna todos ordenado conforme a paginação
	 * */
	@GetMapping("/todos")
	public ResponseEntity<?> todos(Pageable pageable) throws Exception{
		Page<Cliente> page = repository.findAll(pageable);
		cidadeServiceImpl.getCidadeCliente(page.getContent());		
		return new ResponseEntity<>(page, HttpStatus.OK);
	}	
	
	/*
	 * filtro sem paginação, usado no update e delete
	 * */
	@PostMapping("/filtrar")
	public ResponseEntity<?> filtrar(@RequestBody Cliente entity) throws Exception {
	   try {
		  List<Cliente> lista  = service.filtrar(entity);			  
		  return new ResponseEntity<List<Cliente>>(lista, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	/*
	 * filtro com paginação, usado nos campos de filtro das pequisas
	 * */
	@PostMapping("/filtrarPage")
	public ResponseEntity<?> filtrarPage(@RequestBody Cliente entity, Pageable pageable) throws Exception {
	   try {
		   Page<Cliente> page  = service.filtrarPage(entity, pageable);	
		   cidadeServiceImpl.getCidadeCliente(page.getContent());	
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}		
		
	@PostMapping("/salvar")
	 public ResponseEntity<?> salvar(@RequestBody Cliente cliente) throws Exception {
		 try {
			 if (service.cpfCadastrado(cliente.getCpf())) {
				 return new ResponseEntity<String>("CPF já cadastrado: " + cliente.getCpf(), HttpStatus.EXPECTATION_FAILED);	
			 }
			 Cliente clienteSalvo = service.salvar(cliente);
			 return ResponseEntity.ok(clienteSalvo);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao salvar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
	
	@PostMapping("/atualizar")
	 public ResponseEntity<?> atualizar(@RequestBody Cliente cliente) throws Exception {
		 try {			 
			 if (service.cpfUtilizadoParaOutroCliente(cliente)) {
				 return new ResponseEntity<String>("CPF já utilizado em outro cliente: " + cliente.getCpf(), HttpStatus.EXPECTATION_FAILED);	
			 }
			 Cliente clienteSalvo = service.salvar(cliente);
			 return ResponseEntity.ok(clienteSalvo);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao salvar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) throws Exception {
		try {
			service.deletar(id);
			return new ResponseEntity<Object>(HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Este registro não pode ser excluído/possui referência!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@GetMapping("/listar")
	public ResponseEntity<?> todos() throws Exception{
		return new ResponseEntity<>(repository.listarClienteOrdenadoPornome(), HttpStatus.OK);
	}
}
