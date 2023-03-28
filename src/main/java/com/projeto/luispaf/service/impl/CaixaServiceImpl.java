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
	
	public Double getTotalPacoteDoDia(Date dataAtual) {
		return repository.getTotalPacoteDoDia(dataAtual);
	}
	
	public Double getTotalprodutoDoDia(Date dataAtual) {
		return repository.getTotalprodutoDoDia(dataAtual);
	}
	
	public Double getTotalCaixaDoDia(Date dataAtual) {
		Double totalPacote = repository.getTotalPacoteDoDia(dataAtual);
		Double totalPedido = repository.getTotalprodutoDoDia(dataAtual);
		return Double.sum(totalPacote, totalPedido);
	}
	
	public Long getQtdeCaixaCriadoDataAtual(Date dataAtual) {
		return repository.getQtdeCaixaCriadoDataAtual(dataAtual);
	}

	public Caixa salvar(Caixa caixa) {
		return repository.save(caixa);
	}
	
	public Long getQtdeCaixaAbertoOutrasDatas(Date dataAtual) {
		return repository.getQtdeCaixaAbertoOutrasDatas(dataAtual);
	}
	
	public Long getQtdeCaixaAbertoDataAtua(Date dataAtual) {
		return repository.getQtdeCaixaAbertoDataAtua(dataAtual);
	}
	
	public List<Caixa> getCaixasBertoDatasAnteriores(Date dataAbertura) {
		List<Caixa> lista = repository.getCaixasBertoDatasAnteriores(dataAbertura);
		lista.forEach(caixa -> {
			Double totProduto = repository.getTotalproduto(caixa.getDataAbertura());
			Double totPacote = repository.getTotalPacote(caixa.getDataAbertura());
			caixa.setTotalProduto(totProduto);
			caixa.setTotalPacote(totPacote);
			caixa.setTotalCaixa(totProduto + totPacote);
		});
		return lista;
	}
	
	public Caixa getCaixaAbertoDoDia(Date dataAbertura) {
		return repository.getCaixaAbertoDoDia(dataAbertura);
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
