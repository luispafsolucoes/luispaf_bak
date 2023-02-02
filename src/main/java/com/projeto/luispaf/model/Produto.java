package com.projeto.luispaf.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Produto implements Serializable{

	private static final long serialVersionUID = -4439669848434047458L;
	
	@Id
	@GeneratedValue(generator = "autoinc")
	@Column(name = "codproduto")
	private Long codigo;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "valor")
	private Double valor;
	
	@Column(name = "datcadastro")
	private Date dataCadastro;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "datinativacao")
	private Date dataInativacao;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDataInativacao() {
		return dataInativacao;
	}

	public void setDataInativacao(Date dataInativacao) {
		this.dataInativacao = dataInativacao;
	}	
	
}
