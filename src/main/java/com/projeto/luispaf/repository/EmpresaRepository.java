package com.projeto.luispaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.Empresa;

@Transactional(readOnly = true)
public interface EmpresaRepository  extends  JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {

}
