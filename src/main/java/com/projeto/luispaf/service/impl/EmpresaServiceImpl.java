package com.projeto.luispaf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.Empresa;
import com.projeto.luispaf.repository.EmpresaRepository;

@Service
public class EmpresaServiceImpl {
	
	@Autowired
	EmpresaRepository repository;	
	
	public Empresa salvar(Empresa entity) throws Exception {
		return repository.save(entity);
	}

	public void deletar(Long codigo) throws Exception {
		repository.deleteById(codigo);		
	}	

	public Empresa findById(Long id) throws Exception {
		return repository.findById(id).get();
	}
}
