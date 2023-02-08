package com.projeto.luispaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.luispaf.model.Agenda;
import com.projeto.luispaf.repository.AgendaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/versao")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VersionController {
	
	@Autowired
	AgendaRepository agendaRepository;

	@GetMapping("/status")
	public ResponseEntity<?> getTotalPacoteDoDia() throws Exception{
		return new ResponseEntity<>("Tudo OK", HttpStatus.OK);
	}
	
	@GetMapping("/agendadosDiasNteriores")
	public ResponseEntity<?> getAgendadosDiasNteriores() throws Exception{
		
		List<Agenda> lista = agendaRepository.getClientesAgendadosDiasAnteriores();
		
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
}
