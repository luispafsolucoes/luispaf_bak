package com.projeto.luispaf.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.luispaf.model.Cidade;

public interface CidadeService {
	public Cidade salvar(Cidade entity) throws Exception;
	public void deletar(Long codigo) throws Exception;
	public List<Cidade> filtrar(Cidade entity) throws Exception;
	public boolean cidadeJaexiste(Cidade cidade) throws Exception;
	public Page<Cidade> filtrarPage(Cidade entity,  Pageable pageable) throws Exception;
}
