package com.projeto.luispaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.luispaf.model.Usuario;

@Transactional(readOnly = true)
public interface UsuarioRepository  extends  JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario>{

	@Query(nativeQuery = true,
			 value = "SELECT * FROM usuario u \r\n"
			 		+ "   where u.login = :usuario \r\n"
			 		+ "and u.senha = :senha \r\n"
			 		+ "and u.status = 'ATIVO'"		 
			 )
	Usuario getUsuario(@Param("usuario") String usuario, @Param("senha") String senha);
	
	@Query(nativeQuery = true,
			 value = "SELECT * FROM usuario where status = 'ATIVO'"		 
			 )
	 List<Usuario> getUsuariosAtivos();
}
