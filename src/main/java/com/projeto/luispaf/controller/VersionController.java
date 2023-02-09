package com.projeto.luispaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.luispaf.repository.CaixaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/versao")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VersionController {
	
	@Autowired
	CaixaRepository caixaRepository;

	@GetMapping("/status")
	public ResponseEntity<?> getTotalPacoteDoDia() throws Exception{
		try {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			Long qtde = caixaRepository.getQtdeCaixaCriadoDataAtual();
			System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
			
			return new ResponseEntity<>("QTDE: " + qtde, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
		
	}
}
