package com.projeto.luispaf.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "itempacotevendido")
public class ItemPacoteVendido  implements Serializable {

	private static final long serialVersionUID = -1869638907983817034L;
	
	@Id
	@GeneratedValue(generator = "autoinc")
	@Column(name = "coditempacotevendido")
	private Long codigo;
	
	@Column(name = "codpacotevendido")
	private Long codigoPacoteVendido;
	
	@Column(name = "codproduto")
	private Long codigoProduto;
	
	@Column(name = "datinicio")
	private Date dataInicio;
	
	@Column(name = "datfim")
	private Date dataFim;
	
	@Column(name = "codempresa")
	private Long codigoEmpresa;
	
	@Column(name = "status")
	private String status;
	
	@Transient
	Produto produto;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoPacoteVendido() {
		return codigoPacoteVendido;
	}

	public void setCodigoPacoteVendido(Long codigoPacoteVendido) {
		this.codigoPacoteVendido = codigoPacoteVendido;
	}

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Long codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
