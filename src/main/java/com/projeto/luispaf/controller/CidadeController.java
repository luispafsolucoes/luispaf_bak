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

import com.projeto.luispaf.model.Cidade;
import com.projeto.luispaf.repository.CidadeRepository;
import com.projeto.luispaf.service.CidadeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cidade")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CidadeController {
	
	@Autowired
	CidadeService service;
	
	@Autowired
	CidadeRepository repository;
	
	
	@GetMapping("/todos")
	public ResponseEntity<?> todos(Pageable pageable) throws Exception{
		return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
		
	}
	
	@GetMapping("/listar")
	public ResponseEntity<?> todos() throws Exception{
		return new ResponseEntity<>(repository.listarCidadeOrdenadoPornome(), HttpStatus.OK);
	}
		
	@PostMapping("/filtrar")
	public ResponseEntity<?> filtrar(@RequestBody Cidade cidade) throws Exception {
		   try {
			  List<Cidade> lista  = service.filtrar(cidade);			  
			  return new ResponseEntity<List<Cidade>>(lista, HttpStatus.OK);			
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}	
	}
	
	@PostMapping("/filtrarPage")
	public ResponseEntity<?> filtrarPage(@RequestBody Cidade cidade, Pageable pageable) throws Exception {
	   try {
		   Page<Cidade> page  = service.filtrarPage(cidade, pageable);				  
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<?> salvar(@RequestBody Cidade cidade) throws Exception {		
		if (service.cidadeJaexiste(cidade)) {
			return new ResponseEntity<String>("Cidade já cadastrada: " + cidade.getNome(), HttpStatus.EXPECTATION_FAILED);		
		} else {
			Cidade cid = service.salvar(cidade);
			return new ResponseEntity<Cidade>(cid, HttpStatus.OK);			
		}
	}

}
