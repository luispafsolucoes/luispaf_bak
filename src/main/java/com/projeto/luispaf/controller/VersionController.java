package com.projeto.luispaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.luispaf.model.Cidade;
import com.projeto.luispaf.repository.CidadeRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/versao")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VersionController {
	
	@Autowired
	CidadeRepository repository;

	
	
	//Usado apenas para testes no heroku
	@GetMapping("/status")
	public ResponseEntity<?> getTotalPacoteDoDia() throws Exception{
		try {
			List<Cidade> lista = repository.listarCidadeOrdenadoPornome();			
			return new ResponseEntity<>("OKKKKKKKKK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
		
	}
}
