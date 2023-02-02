package com.projeto.luispaf.controller;

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

import com.projeto.luispaf.model.ItemPacote;
import com.projeto.luispaf.model.Pacote;
import com.projeto.luispaf.service.ItemPacoteService;
import com.projeto.luispaf.service.impl.ProdutoServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/itemPacote")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemPacoteController {

	@Autowired
	ItemPacoteService service;
	@Autowired
	ProdutoServiceImpl produtoServiceImpl;
	
	@PostMapping("/filtrarPage")
	public ResponseEntity<?> filtrarPage(@RequestBody Pacote pacote, Pageable pageable) throws Exception {
	   try {
		   Page<ItemPacote> page  = service.filtrarPage(pacote, pageable);
		   produtoServiceImpl.getProdutoItemPacote(page.getContent());
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro(Page): " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
}
