package com.projeto.luispaf.model;

import java.io.Serializable;

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
@Table(name = "itempacote")
public class ItemPacote  implements Serializable {

	private static final long serialVersionUID = -8796145109297835141L;
	
	@Id
	@GeneratedValue(generator = "autoinc")
	@Column(name = "coditempacote")
	private Long codigo;
	
	@Column(name = "codpacote")
	private Long codigoPacote;
	
	@Column(name = "codproduto")
	private Long codigoProduto;
	
	@Transient
	Produto produto;

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

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}	
}
