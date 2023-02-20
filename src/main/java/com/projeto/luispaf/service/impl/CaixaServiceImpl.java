package com.projeto.luispaf.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.luispaf.model.Caixa;
import com.projeto.luispaf.repository.CaixaRepository;

@Service
public class CaixaServiceImpl {
	
	@Autowired
	CaixaRepository repository;
	
	public Double getTotalPacoteDoDia() {
		return repository.getTotalPacoteDoDia();
	}
	
	public Double getTotalprodutoDoDia() {
		return repository.getTotalprodutoDoDia();
	}
	
	public Double getTotalCaixaDoDia() {
		Double totalPacote = repository.getTotalPacoteDoDia();
		Double totalPedido = repository.getTotalprodutoDoDia();
		return Double.sum(totalPacote, totalPedido);
	}
	
	public Long getQtdeCaixaCriadoDataAtual() {
		return repository.getQtdeCaixaCriadoDataAtual();
	}

	public Caixa salvar(Caixa caixa) {
		return repository.save(caixa);
	}
	
	public Long getQtdeCaixaAbertoOutrasDatas() {
		return repository.getQtdeCaixaAbertoOutrasDatas();
	}
	
	public Long getQtdeCaixaAbertoDataAtua() {
		return repository.getQtdeCaixaAbertoDataAtua();
	}
	
	public List<Caixa> getCaixasBertoDatasAnteriores() {
		List<Caixa> lista = repository.getCaixasBertoDatasAnteriores();
		lista.forEach(caixa -> {
			Double totProduto = repository.getTotalproduto(caixa.getDataAbertura());
			Double totPacote = repository.getTotalPacote(caixa.getDataAbertura());
			caixa.setTotalProduto(totProduto);
			caixa.setTotalPacote(totPacote);
			caixa.setTotalCaixa(totProduto + totPacote);
		});
		return lista;
	}
	
	public Caixa getCaixaAbertoDoDia() {
		return repository.getCaixaAbertoDoDia();
	}
	
	public Double getTotalPacote(Date dataCaixa) {
		return repository.getTotalPacote(dataCaixa);
	}
	
	public Double getTotalproduto(Date dataCaixa) {
		return repository.getTotalproduto(dataCaixa);
	}
	
	public Double getTotalprodutoPorPeriodo(Date dataInicio, Date DataFim) {
		return repository.getTotalprodutoPorPeriodo(dataInicio, DataFim);
	}
	
	public Double getTotalpacotePorPeriodo(Date dataInicio, Date DataFim) {
		return repository.getTotalpacotePorPeriodo(dataInicio, DataFim);
	}
	
	public Double getTotalcaixaPorPeriodo(Date dataInicio, Date DataFim) {
		return repository.getTotalcaixaPorPeriodo(dataInicio, DataFim);
	}
	
	public Date getDataAtual() {
		return repository.getDataAtual();
	}
}
