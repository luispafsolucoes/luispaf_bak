package com.projeto.luispaf.model.DTO;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class RelatorioPorPeriodoDTO {
	
	private Double totalproduto;
	private Double totalpacote;
	private Double totalcaixa;
	private Long   qtdePacote;
	private Long   qtdeProduto;
	
	public RelatorioPorPeriodoDTO(Double totalproduto, Double totalpacote, Double totalcaixa, Long qtdePacote, Long qtdeProduto) {
		this.totalproduto = totalproduto;
		this.totalpacote = totalpacote;
		this.totalcaixa = totalcaixa;
		this.qtdePacote = qtdePacote;
		this.qtdeProduto = qtdeProduto;
	}
	
	
	public Double getTotalproduto() {
		return totalproduto;
	}
	public void setTotalproduto(Double totalproduto) {
		this.totalproduto = totalproduto;
	}
	public Double getTotalpacote() {
		return totalpacote;
	}
	public void setTotalpacote(Double totalpacote) {
		this.totalpacote = totalpacote;
	}
	public Double getTotalcaixa() {
		return totalcaixa;
	}
	public void setTotalcaixa(Double totalcaixa) {
		this.totalcaixa = totalcaixa;
	}
	public Long getTotalPacote() {
		return qtdePacote;
	}
	public void setTotalPacote(Long totalPacote) {
		this.qtdePacote = totalPacote;
	}
	public Long getTotalProduto() {
		return qtdeProduto;
	}
	public void setTotalProduto(Long totalProduto) {
		this.qtdeProduto = totalProduto;
	}		
}
