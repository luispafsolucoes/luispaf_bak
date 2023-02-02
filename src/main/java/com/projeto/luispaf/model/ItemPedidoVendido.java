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
@Table(name = "itempedidovendido")
public class ItemPedidoVendido implements Serializable {

	private static final long serialVersionUID = -1753814862504764046L;
	
	@Id
	@GeneratedValue(generator = "autoinc")
	@Column(name = "coditempedidovendido")
	private Long codigo;
	
	@Column(name = "codpedidovendido")
	private Long codigoPedidoVendido;
	
	@Column(name = "codproduto")
	private Long codigoProduto;
	
	@Column(name = "codempresa")
	private Long codigoEmpresa;
	
	@Transient
	Produto produto;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoPedidoVendido() {
		return codigoPedidoVendido;
	}

	public void setCodigoPedidoVendido(Long codigoPedidoVendido) {
		this.codigoPedidoVendido = codigoPedidoVendido;
	}

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
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
	
}
