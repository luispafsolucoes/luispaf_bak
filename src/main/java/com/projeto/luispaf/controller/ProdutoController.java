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
import com.projeto.luispaf.model.Produto;
import com.projeto.luispaf.repository.ProdutoRepository;
import com.projeto.luispaf.service.ProdutoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProdutoController {
	
	@Autowired
	ProdutoService service;
	
	@Autowired
	ProdutoRepository repository;
	
	
	@GetMapping("/todos")
	public ResponseEntity<?> todos(Pageable pageable) throws Exception{
		return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
	}
			
	@PostMapping("/filtrar")
	public ResponseEntity<?> filtrar(@RequestBody Produto entity) throws Exception {
	   try {
		  List<Produto> lista  = service.filtrar(entity);			  
		  return new ResponseEntity<List<Produto>>(lista, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/filtrarPage")
	public ResponseEntity<?> filtrarPage(@RequestBody Produto entity, Pageable pageable) throws Exception {
	   try {
		   Page<Produto> page  = service.filtrarPage(entity, pageable);				  
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
	public ResponseEntity<?> salvar(@RequestBody Produto entity) throws Exception {	
		try {
			service.validarValorProduto(entity);
			
			if (service.produtoJaexiste(entity)) {
				return new ResponseEntity<String>("Produto já cadastrado: " + entity.getNome(), HttpStatus.EXPECTATION_FAILED);		
			}
			
			Produto prod = service.salvar(entity);
			return new ResponseEntity<Produto>(prod, HttpStatus.OK);	
		} catch (Exception e) {
			throw new Exception("Falha ao salvar produto: " + e.getMessage());
		}		
	}
	
	@PostMapping("/atualizar")
	 public ResponseEntity<?> atualizar(@RequestBody Produto entity) throws Exception {
		 try {	
			 Produto produtoSalvo = service.salvar(entity);
			 return ResponseEntity.ok(produtoSalvo);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao salvar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
	
	@PostMapping("/buscarProdutoPacote")
	public ResponseEntity<?> buscarProdutoPacote(@RequestBody Pacote entity) throws Exception {
	   try {
		  List<Produto> lista  = service.buscarProdutoPacote(entity.getCodigo());			  
		  return new ResponseEntity<List<Produto>>(lista, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao bustar itens do pacote: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@GetMapping("/listar")
	public ResponseEntity<?> todos() throws Exception{
		return new ResponseEntity<>(repository.listarProdutoAtivoOrdenadoPornome(), HttpStatus.OK);
	}
}
