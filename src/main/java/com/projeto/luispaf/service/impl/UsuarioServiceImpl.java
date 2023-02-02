package com.projeto.luispaf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.Usuario;
import com.projeto.luispaf.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Usuario getUsuario(String usuario, String senha) {
		return usuarioRepository.getUsuario(usuario, senha);
	}
	
	public  List<Usuario> getUsuariosAtivos() {
		return usuarioRepository.getUsuariosAtivos();
	}
	
	public Usuario salvar(Usuario entity) {
		return usuarioRepository.save(entity);
	}
	
	public Usuario getUsuarioPorId(Long id) {
		return usuarioRepository.findById(id).get();
	}

}
