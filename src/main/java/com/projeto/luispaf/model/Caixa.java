package com.projeto.luispaf.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "caixa")
public class Caixa implements Serializable {

	private static final long serialVersionUID = 4796802705381476755L;
	
	@Id
	@GeneratedValue(generator = "autoinc")
	@Column(name = "codigocaixa")
	private Long codigo;
	
	@Column(name = "totalproduto")
	private Double totalProduto;
	
	@Column(name = "totalpacote")
	private Double totalPacote;
	
	@Column(name = "totalcaixa")
	private Double totalCaixa;
	
	@Column(name = "databertura")
	private Date dataAbertura;
	
	@Column(name = "datfechamento")
	private Date dataFechamento;
	
	@Column(name = "codempresa")
	private Long codigoEmpresa;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "codusuario")
	private Long codigoUsuario;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Double getTotalProduto() {
		return totalProduto;
	}

	public void setTotalProduto(Double totalProduto) {
		this.totalProduto = totalProduto;
	}

	public Double getTotalPacote() {
		return totalPacote;
	}

	public void setTotalPacote(Double totalPacote) {
		this.totalPacote = totalPacote;
	}

	public Double getTotalCaixa() {
		return totalCaixa;
	}

	public void setTotalCaixa(Double totalCaixa) {
		this.totalCaixa = totalCaixa;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Long codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCodigoUsuario() {	
		return codigoUsuario;
	}

	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
}
