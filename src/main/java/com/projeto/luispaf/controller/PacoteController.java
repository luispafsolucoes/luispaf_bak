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

import com.projeto.luispaf.model.Pacote;
import com.projeto.luispaf.repository.ItemPacoteRepository;
import com.projeto.luispaf.repository.PacoteRepository;
import com.projeto.luispaf.service.ItemPacoteService;
import com.projeto.luispaf.service.PacoteService;
import com.projeto.luispaf.service.impl.PacoteServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pacote")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PacoteController {
	
	@Autowired
	PacoteService service;
	
	@Autowired
	ItemPacoteService itemPacoteService;
	
	@Autowired
	PacoteRepository repository;
	
	@Autowired
	ItemPacoteRepository itemRepository;
	
	@Autowired
	PacoteServiceImpl serviceImpl;
	
	@GetMapping("/todos")
	public ResponseEntity<?> todos(Pageable pageable) throws Exception{
		Page<Pacote> page = repository.findAll(pageable);
		serviceImpl.getItensPacote(page.getContent());		
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
			
	@PostMapping("/filtrar")
	public ResponseEntity<?> filtrar(@RequestBody Pacote entity) throws Exception {
	   try {
		  List<Pacote> lista  = service.filtrar(entity);
		  serviceImpl.getItensPacote(lista);	
		  return new ResponseEntity<List<Pacote>>(lista, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/filtrarPage")
	public ResponseEntity<?> filtrarPage(@RequestBody Pacote entity, Pageable pageable) throws Exception {
	   try {
		   Page<Pacote> page  = service.filtrarPage(entity, pageable);				  
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro(Page): " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salvar(@RequestBody Pacote entity) throws Exception {	
		try {
			service.validarValorPacote(entity);
			
			if (service.pacoteJaExiste(entity)) {
				return new ResponseEntity<String>("Pacote já cadastrado: " + entity.getNome(), HttpStatus.EXPECTATION_FAILED);		
			}
			
			Pacote pacotesalvo = service.salvar(entity);
			itemPacoteService.salvar(pacotesalvo);
			return new ResponseEntity<Pacote>(pacotesalvo, HttpStatus.OK);	
		} catch (Exception e) {
			throw new Exception("Falha ao salvar pacote: " + e.getMessage());
		}		
	}
	
	@PostMapping("/inativar")
	 public ResponseEntity<?> atualizar(@RequestBody Pacote entity) throws Exception {
		 try {	
			 Pacote entitySalvo = service.salvar(entity);
			 return ResponseEntity.ok(entitySalvo);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao salvar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
	
	
	@GetMapping("/listarPacotesAtivosOrdenadoPornome")
	public ResponseEntity<?> todos() throws Exception{
		return new ResponseEntity<>(serviceImpl.listarPacotesAtivosOrdenadoPornome(), HttpStatus.OK);
	}
}
