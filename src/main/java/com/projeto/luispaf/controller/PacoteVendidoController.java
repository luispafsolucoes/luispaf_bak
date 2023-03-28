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
import com.projeto.luispaf.model.PacoteVendido;
import com.projeto.luispaf.service.impl.CaixaServiceImpl;
import com.projeto.luispaf.service.impl.ClienteServiceImpl;
import com.projeto.luispaf.service.impl.ItemPacoteVendidoServiceImpl;
import com.projeto.luispaf.service.impl.PacoteVendidoServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pacoteVendido")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PacoteVendidoController {

	@Autowired
	PacoteVendidoServiceImpl service;	
	@Autowired
	ClienteServiceImpl clienteServiceImpl;
	@Autowired
	ItemPacoteVendidoServiceImpl itemPacoteVendidoServiceImpl;
	@Autowired
	CaixaServiceImpl caixaServiceImpl;
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salvar(@RequestBody PacoteVendido entity) throws Exception {	
		try {	
			// Pego a data inicio, porem ela tem ser sempre a data atual vindo do front
			Long qtdeCaixaAbertoDataAtual = caixaServiceImpl.getQtdeCaixaAbertoDataAtua(entity.getDataInicio());
			
			if (qtdeCaixaAbertoDataAtual > 0) {
				PacoteVendido pacoteVendidoSalvo = service.salvar(entity);
				itemPacoteVendidoServiceImpl.salvarItensPacote(pacoteVendidoSalvo);
				return new ResponseEntity<PacoteVendido>(pacoteVendidoSalvo, HttpStatus.OK);
			}
			
			// Só pode deixar gerar uma venda se o caixa já estiver aberto
			return new ResponseEntity<String>("Ainda não existe caixa aberto para a data atual!", HttpStatus.EXPECTATION_FAILED);				
		} catch (Exception e) {
			throw new Exception("Falha ao salvar PacoteVendido: " + e.getMessage());
		}		
	}
	
	@PostMapping("/filtrar")
	public ResponseEntity<?> filtrar(@RequestBody PacoteVendido entity) throws Exception {
	   try {
		  List<PacoteVendido> lista  = service.filtrar(entity);	
		  // Seta o cliente e o pacote
		  clienteServiceImpl.getClientePacoteVendido(lista);
		  return new ResponseEntity<List<PacoteVendido>>(lista, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/filtrarPage")
	public ResponseEntity<?> filtrarPage(@RequestBody PacoteVendido entity, Pageable pageable) throws Exception {
	   try {
		   Page<PacoteVendido> page  = service.filtrarPage(entity, pageable);
		   // Seta o cliente e o pacote
		   clienteServiceImpl.getClientePacoteVendido(page.getContent());
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro(Page): " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/cancelarPacote")
	public ResponseEntity<?> cancelarPacote(@RequestBody PacoteVendido entity) throws Exception {	
		try {	
			// ##### iserir aqui a regra de não deixar cancelar pacote vendido, caso o caixa do dia já estiver fexado
						
			PacoteVendido pcvSalvo = service.salvar(entity);
			itemPacoteVendidoServiceImpl.cancelarItensPacoteVendido(pcvSalvo);
			return new ResponseEntity<PacoteVendido>(pcvSalvo, HttpStatus.OK);	
		} catch (Exception e) {
			throw new Exception("Falha ao salvar PacoteVendido: " + e.getMessage());
		}		
	}
	
	@PostMapping("/inativarItemPacoteVendido")
	public ResponseEntity<?> inativarItemPacoteVendido(@RequestBody ItemPacoteVendido entity) throws Exception {
	   try {
		   //Caso seja o ultimo item inativado, inativa o pai tambem
		   List<PacoteVendido> lista = itemPacoteVendidoServiceImpl.inativarItemPacoteVendido(entity);
		   // Seta o cliente e o pacote
		   clienteServiceImpl.getClientePacoteVendido(lista);
		  return new ResponseEntity<List<PacoteVendido>>(lista, HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}
