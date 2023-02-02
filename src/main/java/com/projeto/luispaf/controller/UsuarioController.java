package com.projeto.luispaf.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.luispaf.model.Usuario;
import com.projeto.luispaf.service.impl.UsuarioServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController {
	
	@Autowired
	UsuarioServiceImpl usuarioServiceImpl;
	
	@PostMapping("/logar")
	public ResponseEntity<?> logar(@RequestBody Usuario usuario) throws Exception {
	   try {
		  Usuario usu  = usuarioServiceImpl.getUsuario(usuario.getLogin(), usuario.getSenha());	
		  if (Objects.nonNull(usu)) {
			  usu.setLogado(1L);
			  Usuario usuSalvo = usuarioServiceImpl.salvar(usu);			  
			  return new ResponseEntity<>(usuSalvo, HttpStatus.OK);	
		  }
		  return new ResponseEntity<String>("Usuário/Senha invalidos!", HttpStatus.EXPECTATION_FAILED);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao filtrar usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/deslogar")
	public ResponseEntity<?> deslogar(@RequestBody Usuario usuario) throws Exception {
	   try {
		  Usuario usu  = usuarioServiceImpl.getUsuario(usuario.getLogin(), usuario.getSenha());	
		  if (Objects.nonNull(usu)) {
			  usu.setLogado(0L);
			  usuarioServiceImpl.salvar(usu);		  
			  return new ResponseEntity<>(HttpStatus.OK);	
		  }
		  return new ResponseEntity<String>("Usuário/Senha invalidos!", HttpStatus.EXPECTATION_FAILED);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao deslogar usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/validarLogin")
	public ResponseEntity<?> validarLogin(@RequestBody Usuario usuario) throws Exception {
	   try {
		  Usuario usu  = usuarioServiceImpl.getUsuarioPorId(usuario.getCodigo());
		  if (usu.getLogado() > 0) {
			  return new ResponseEntity<>(usu, HttpStatus.OK);	
		  }		  
		  return new ResponseEntity<>(new Usuario(), HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao validarLogin usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/validausuarioparaagendar")
	public ResponseEntity<?> validausuarioparaagendar(@RequestBody Usuario usuario) throws Exception {
	   try {
		  Usuario usu  = usuarioServiceImpl.getUsuario(usuario.getLogin(), usuario.getSenha());	
		  if (Objects.nonNull(usu)) {			  
			  return new ResponseEntity<>(usu, HttpStatus.OK);	
		  }
		  return new ResponseEntity<String>("Usuário/Senha invalidos!", HttpStatus.EXPECTATION_FAILED);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao filtrar usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/getUsuario")
	public ResponseEntity<?> getUsuario(@RequestBody Usuario usuario) throws Exception {
	   try {
		  Usuario usu  = usuarioServiceImpl.getUsuarioPorId(usuario.getCodigo());
		  if (Objects.nonNull(usu)) {
			  return new ResponseEntity<>(usu, HttpStatus.OK);	
		  }	
		  
		  return new ResponseEntity<String>("Falha ao buscar usuario: " + usuario.getCodigo(), HttpStatus.INTERNAL_SERVER_ERROR);			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao validarLogin usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
}
