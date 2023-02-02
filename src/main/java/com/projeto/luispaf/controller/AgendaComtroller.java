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

import com.projeto.luispaf.model.Agenda;
import com.projeto.luispaf.model.Empresa;
import com.projeto.luispaf.model.Status;
import com.projeto.luispaf.model.TipoProduto;
import com.projeto.luispaf.repository.ClienteRepository;
import com.projeto.luispaf.service.impl.AgendaServiceImpl;
import com.projeto.luispaf.service.impl.ClienteServiceImpl;
import com.projeto.luispaf.service.impl.EmpresaServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agenda")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AgendaComtroller {
	
	@Autowired
	AgendaServiceImpl agendaServiceImpl;
	@Autowired
	EmpresaServiceImpl empresaServiceImpl;
	@Autowired
	ClienteRepository repository;
	@Autowired
	ClienteServiceImpl clienteServiceImpl;
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salvar(@RequestBody Agenda agenda) throws Exception {		
		try {
			Empresa emp = empresaServiceImpl.findById(agenda.getCodigoEmpresa());
			String msg = "Quantidade de cliente excedida para esse horário: ";
			Boolean tipoProdutoCorreto = false;
			
			if (TipoProduto.NATURAL.name().equals(agenda.getTipoproduto())) {
				tipoProdutoCorreto = true;
				Long qtde = agendaServiceImpl.getQtdeAgendadaHora(agenda.getCodigoEmpresa(), 
																  Status.ATIVO.name(), 
																  agenda.getHorario(), 
																  TipoProduto.NATURAL.name(),
																  agenda.getDataAgenda());
							
				if (qtde >= emp.getQtdeporhoranatural()) {
					return new ResponseEntity<String>(msg + agenda.getHorario() , HttpStatus.EXPECTATION_FAILED);
				}			
			}
			
			if (TipoProduto.ARTIFICIAL.name().equals(agenda.getTipoproduto())) {
				tipoProdutoCorreto = true;
				Long qtde = agendaServiceImpl.getQtdeAgendadaHora(agenda.getCodigoEmpresa(), 
																  Status.ATIVO.name(), 
																  agenda.getHorario(), 
																  TipoProduto.ARTIFICIAL.name(),
																  agenda.getDataAgenda());
							
				if (qtde >= emp.getQtdeporhoraartificial()) {
					return new ResponseEntity<String>(msg + agenda.getHorario() , HttpStatus.EXPECTATION_FAILED);
				}			
			}
			
			if (tipoProdutoCorreto) {
				Agenda agendaSalva = agendaServiceImpl.salvar(agenda);
				return new ResponseEntity<>(agendaSalva, HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("Tipo de produto incorreto: " + agenda.getTipoproduto() , HttpStatus.EXPECTATION_FAILED);	
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao salvar agenda: " + e.getLocalizedMessage() , HttpStatus.EXPECTATION_FAILED);	
		}			
	}
	
	@PostMapping("/filtrarPage")
	public ResponseEntity<?> filtrarPage(@RequestBody Agenda entity, Pageable pageable) throws Exception {
	   try {		   		   
		   Page<Agenda> page  = agendaServiceImpl.filtrarPage(entity, pageable);		   
		   clienteServiceImpl.setCliente(page.getContent());
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	
	@PostMapping("/filtrarPorData")
	public ResponseEntity<?> filtrarPorData(@RequestBody Agenda entity, Pageable pageable) throws Exception {
	   try {		   		   
		   Page<Agenda> page  = agendaServiceImpl.getAgendaPorData(entity.getDataAgenda(), pageable);  
		   clienteServiceImpl.setCliente(page.getContent());
		  return new ResponseEntity<>(page, HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao filtrar registro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) throws Exception {
		try {
			agendaServiceImpl.deletar(id);
			return new ResponseEntity<Object>(HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Este registro não pode ser excluído/possui referência!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
