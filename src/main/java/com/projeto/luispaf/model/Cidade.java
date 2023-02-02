package com.projeto.luispaf.model;

import java.io.Serializable;

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
@Table(name = "cidade")
public class Cidade implements Serializable{

	private static final long serialVersionUID = -5165258352340727328L;
	
	@Id
	@GeneratedValue(generator = "autoinc")
	@Column(name = "codcidade")
	private Long codigo;
	
	@Column(name = "nome")	
	private String nome;
	
	@Column(name = "uf")
	private String uf;
	
	public Long getCodigo() { return codigo; }
	 
	public void setCodigo(Long codigo) { this.codigo = codigo; }
	  
	public String getNome() { return nome; }
	 
	public void setNome(String nome) { this.nome = nome; }
	  
	public String getUf() { return uf; }
	  
	public void setUf(String uf) { this.uf = uf; }	 
}
