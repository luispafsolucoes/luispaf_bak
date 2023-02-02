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
@Table(name = "empresa")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 5900903642469159094L;
	
	@Id
	@GeneratedValue(generator = "autoinc")
	@Column(name = "codempresa")
	private Long codigo;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "datcadastro")
	private Date datacadastro;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "contato")
	private String contato;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "qtdeporhoranatural")
	private Long qtdeporhoranatural;
	
	@Column(name = "qtdeporhoraartificial")
	private Long qtdeporhoraartificial;

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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Date datacadastro) {
		this.datacadastro = datacadastro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getQtdeporhoranatural() {
		return qtdeporhoranatural;
	}

	public void setQtdeporhoranatural(Long qtdeporhoranatural) {
		this.qtdeporhoranatural = qtdeporhoranatural;
	}

	public Long getQtdeporhoraartificial() {
		return qtdeporhoraartificial;
	}

	public void setQtdeporhoraartificial(Long qtdeporhoraartificial) {
		this.qtdeporhoraartificial = qtdeporhoraartificial;
	}
}
