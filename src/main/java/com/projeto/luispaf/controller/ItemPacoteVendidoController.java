package com.projeto.luispaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.luispaf.model.ItemPacoteVendido;
import com.projeto.luispaf.service.impl.ItemPacoteVendidoServiceImpl;
import com.projeto.luispaf.service.impl.ProdutoServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/itemPacoteVendido")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemPacoteVendidoController {
	
	@Autowired
	ItemPacoteVendidoServiceImpl service;
	
	@Autowired
	ProdutoServiceImpl produtoServiceImpl;
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salvar(@RequestBody ItemPacoteVendido entity) throws Exception {	
		try {			
			ItemPacoteVendido item = service.salvar(entity);
			return new ResponseEntity<ItemPacoteVendido>(item, HttpStatus.OK);	
		} catch (Exception e) {
			throw new Exception("Falha ao salvar pacote: " + e.getMessage());
		}		
	}
	
	@PostMapping("/filtrar")
	public ResponseEntity<?> filtrar(@RequestBody ItemPacoteVendido entity) throws Exception {
	   try {
		  List<ItemPacoteVendido> lista  = service.filtrar(entity);	
		  produtoServiceImpl.getProdutoItemPacoteVendido(lista);
		  return new ResponseEntity<List<ItemPacoteVendido>>(lista, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/filtrarPage")
	public ResponseEntity<?> filtrarPage(@RequestBody ItemPacoteVendido entity, Pageable pageable) throws Exception {
	   try {
		   Page<ItemPacoteVendido> page  = service.filtrarPage(entity, pageable);
		   produtoServiceImpl.getProdutoItemPacoteVendido(page.getContent());
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro(Page): " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

}
