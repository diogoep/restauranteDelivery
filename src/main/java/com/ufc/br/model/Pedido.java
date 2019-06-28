package com.ufc.br.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY, optional= false)
	private Cliente cliente;
	
	@OneToMany
	private List<Item> itens;
	
	private float valorCompra;
	
	@Column(nullable = false)
	private Date dataConfirmacao;
	
	
	@Column
	private Date dataPedidoAceito;
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public float getValorCompra() {
		return valorCompra;
	}
	
	public void setValorCompra(float valorCompra) {
		this.valorCompra = valorCompra;
	}
	
	public Date getDataConfirmacao() {
		return dataConfirmacao;
	}
	
	public void setDataConfirmacao(Date dataConfirmacao) {
		this.dataConfirmacao = dataConfirmacao;
	}

	public Date getDataPedidoAceito() {
		return dataPedidoAceito;
	}

	public void setDataPedidoAceito(Date dataPedidoAceito) {
		this.dataPedidoAceito = dataPedidoAceito;
	}


	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}