package com.projeto.luispaf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.projeto.luispaf.model.Agenda;
import com.projeto.luispaf.model.Usuario;
import com.projeto.luispaf.repository.AgendaRepository;
import com.projeto.luispaf.service.impl.UsuarioServiceImpl;

@Component
public class Agendamentos {
//deu certo commit
	@Autowired
	UsuarioServiceImpl usuarioServiceImpl;
	@Autowired
	AgendaRepository agendaRepository;

	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long HORA = MINUTO * 60;
	private final long TRES_HORAS = HORA * 3;

	private static final Long DESLOGADO = 0L;

	/**
	 * Desloga o usuario a cada 3 horas
	 */
	@Scheduled(fixedDelay = TRES_HORAS)
	public void deslogarUsuario() throws InterruptedException {
		List<Usuario> lista = usuarioServiceImpl.getUsuariosAtivos();
		lista.forEach(usu -> {
			usu.setLogado(DESLOGADO);
			usuarioServiceImpl.salvar(usu);
		});
	}

	/**
	 * Inativa as agendas dos dias anteriores
	 */
	
	 @Scheduled(fixedDelay = TRES_HORAS)
	 public void InativarAgendas() throws InterruptedException{ 
		 List<Agenda> lista = agendaRepository.getClientesAgendadosDiasAnteriores(); 
		 lista.forEach(ag -> {
			 ag.setStatus("INATIVO"); agendaRepository.save(ag); 
		 }); 
	 }
	
}
