package com.ufc.br.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@SuppressWarnings("serial")
@Entity
public class Gerente implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigoGerente;
	
	@NotBlank(message = "Nome não pode ser vazio!")
	private String nomeRestauranteGerente;
	
	@Size(min=14,max=14)
	@Column(length=14)
	@NotBlank(message = "CNPJ não pode ser vazio!")
	private String cnpjGerente;
	
	@NotBlank(message = "Senha não pode ser vazia!")
	private String senhaGerente;
	
	public Long getCodigoGerente() {
		return codigoGerente;
	}
	public void setCodigoGerente(Long codigoGerente) {
		this.codigoGerente = codigoGerente;
	}
	public String getNomeRestauranteGerente() {
		return nomeRestauranteGerente;
	}
	public void setNomeRestauranteGerente(String nomeRestauranteGerente) {
		this.nomeRestauranteGerente = nomeRestauranteGerente;
	}
	public String getCnpjGerente() {
		return cnpjGerente;
	}
	public void setCnpjGerente(String cnpjGerente) {
		this.cnpjGerente = cnpjGerente;
	}
	public String getSenhaGerente() {
		return senhaGerente;
	}
	public void setSenhaGerente(String senhaGerente) {
		this.senhaGerente = senhaGerente;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "gerentes_roles",
			joinColumns = @JoinColumn(
					name = "gerente_codigo", referencedColumnName = "codigoGerente"), 
	        inverseJoinColumns = @JoinColumn(
	        		name = "role_id", referencedColumnName = "papel"))
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.roles;
	}
	
	@Override
	public String getPassword() {
		return this.senhaGerente;
	}
	@Override
	public String getUsername() {
		return this.cnpjGerente;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
