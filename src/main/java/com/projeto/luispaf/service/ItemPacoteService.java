package com.projeto.luispaf.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.luispaf.model.ItemPacote;
import com.projeto.luispaf.model.Pacote;

public interface ItemPacoteService {

	public void salvar(Pacote pacote) throws Exception;
	
	public List<ItemPacote> buscarItensPorPacote(Long codigoPacote) throws Exception;

	public Page<ItemPacote> filtrarPage(Pacote entity, Pageable pageable) throws Exception;
}
