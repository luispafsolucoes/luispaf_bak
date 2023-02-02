package com.projeto.luispaf.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table(name = "pacotevendido")
public class PacoteVendido implements Serializable {

	private static final long serialVersionUID = -8556508102365021084L;
	
	@Id
	@GeneratedValue(generator = "autoinc")
	@Column(name = "codpacotevendido")
	private Long codigo;
	
	@Column(name = "codpacote")
	private Long codigoPacote;
	
	@Column(name = "codcliente")
	private Long codigoCliente;
	
	@Column(name = "codempresa")
	private Long codigoEmpresa;
	
	@Column(name = "datinicio")
	private Date dataInicio;
	
	@Column(name = "datfim")
	private Date dataFim;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "validadepacote")
	private Date validadePacote;
	
	@Transient
	private List<ItemPacoteVendido> itens;
	
	@Transient
	Cliente cliente;
	
	@Transient
	Pacote pacote;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoPacote() {
		return codigoPacote;
	}

	public void setCodigoPacote(Long codigoPacote) {
		this.codigoPacote = codigoPacote;
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Long codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
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

	public List<ItemPacoteVendido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPacoteVendido> itens) {
		this.itens = itens;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pacote getPacote() {
		return pacote;
	}

	public void setPacote(Pacote pacote) {
		this.pacote = pacote;
	}

	public Date getValidadePacote() {
		return validadePacote;
	}

	public void setValidadePacote(Date validadePacote) {
		this.validadePacote = validadePacote;
	}		
}
