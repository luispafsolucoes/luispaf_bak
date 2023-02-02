package com.projeto.luispaf.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.luispaf.model.Pacote;

public interface PacoteService {

	public Pacote salvar(Pacote entity) throws Exception;
	public void deletar(Long codigo) throws Exception;
	public List<Pacote> filtrar(Pacote entity) throws Exception;
	public boolean pacoteJaExiste(Pacote entity) throws Exception;
	public Page<Pacote> filtrarPage(Pacote entity,  Pageable pageable) throws Exception;
	public void validarValorPacote(Pacote entity) throws Exception;
}
