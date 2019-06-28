package com.ufc.br.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.NumberFormat;

@Entity
public class Prato {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigoPrato;
	
	private String extensaoImagemPrato;
	
	@NotBlank(message = "Nome não pode ser vazio!")
	private String nomePrato;

	
	@Column(columnDefinition = "boolean default true")
	private Boolean ativo = true;
	
	@Column(columnDefinition="Decimal(10,2) default '0.0'")
	@NumberFormat(pattern = "##,#.00")
	private BigDecimal precoPrato;
	
	public Long getCodigoPrato() {
		return codigoPrato;
	}
	
	public void setCodigoPratoPrato(Long codigoPrato) {
		this.codigoPrato = codigoPrato;
	}
	
	public String getNomePrato() {
		return nomePrato;
	}
	
	public void setNomePrato(String nomePrato) {
		this.nomePrato = nomePrato;
	}
	
	public String getExtensaoImagemPrato() {
		return extensaoImagemPrato;
	}
	
	public void setExtensaoImagemPrato(String extensao) {
		this.extensaoImagemPrato = extensao;
	}
	
	public BigDecimal getPrecoPrato() {
		return precoPrato;
	}
	
	public void setPrecoPrato(BigDecimal  precoPrato) {
		this.precoPrato = precoPrato;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setCodigoPrato(Long codigoPrato) {
		this.codigoPrato = codigoPrato;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}