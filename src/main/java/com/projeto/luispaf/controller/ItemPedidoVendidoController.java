package com.projeto.luispaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.luispaf.model.ItemPedidoVendido;
import com.projeto.luispaf.model.PedidoVendido;
import com.projeto.luispaf.service.impl.ItemPedidoVendidoServiceImpl;
import com.projeto.luispaf.service.impl.ProdutoServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/itemPedidoVendido")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemPedidoVendidoController {

	@Autowired
	ItemPedidoVendidoServiceImpl itemPedidoVendidoServiceImpl;
	@Autowired
	ProdutoServiceImpl produtoServiceImpl;
	
	@PostMapping("/salvar")
	 public ResponseEntity<?> salvar(@RequestBody ItemPedidoVendido entity) throws Exception {
		 try {
			 ItemPedidoVendido itemPedidoVendido = itemPedidoVendidoServiceImpl.salvar(entity);
			 return ResponseEntity.ok(itemPedidoVendido);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao salvar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) throws Exception {
		try {
			itemPedidoVendidoServiceImpl.deletar(id);
			return new ResponseEntity<Object>(HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Este registro não pode ser excluído/possui referência!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/itensPedidoVenda")
	public ResponseEntity<?> getItensPedidoVenda(@RequestBody PedidoVendido entity, Pageable pageable) throws Exception {
	   try {
		   Page<ItemPedidoVendido> page  = itemPedidoVendidoServiceImpl.filtrarPage(entity, pageable);
		   produtoServiceImpl.getProdutoItemPedidoVendido(page.getContent());
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro(Page): " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
}
