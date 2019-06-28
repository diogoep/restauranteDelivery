package com.ufc.br.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@SuppressWarnings("serial")
@Entity
public class Cliente implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigoCliente;
	
	@NotBlank(message = "Nome não pode ser vazio!")
	private String nomeCliente;
	
	@Size(min=11,max=11)
	@Column(length=11, unique=true)
	@NotBlank(message = "CPF não pode ser vazio!")
	private String cpfCliente;
	
	@NotNull(message = "Data de nascimento não pode ser vazia!")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(nullable = false, columnDefinition = "DATE")
	private Date dataNascimentoCliente;
	
	@NotBlank(message = "Endereço não pode ser vazio!")
	private String enderecoCliente;
	
	@NotBlank(message = "Senha não pode ser vazia!")
	private String senhaCliente;
	
	@NotBlank(message = "Email não pode ser vazio!")
	private String emailCliente;
		
	public Long getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getCpfCliente() {
		return cpfCliente;
	}
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}
	public Date getDataNascimentoCliente() {
		return dataNascimentoCliente;
	}
	public void setDataNascimentoCliente(Date dataNascimentoCliente) {
		this.dataNascimentoCliente = dataNascimentoCliente;
	}
	public String getEnderecoCliente() {
		return enderecoCliente;
	}
	public void setEnderecoCliente(String enderecoCliente) {
		this.enderecoCliente = enderecoCliente;
	}
	public String getSenhaCliente() {
		return senhaCliente;
	}
	public void setSenhaCliente(String senhaCliente) {
		this.senhaCliente = senhaCliente;
	}
	public String getEmailCliente() {
		return emailCliente;
	}
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "clientes_roles",
			joinColumns = @JoinColumn(
					name = "cliente_codigo", referencedColumnName = "codigoCliente"), 
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
		return this.senhaCliente;
	}
	
	@Override
	public String getUsername() {
		return this.cpfCliente;
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
	
	/*
	Calendar c = Calendar.getInstance();
    Date data = c.getTime();
     
    DateFormat f = DateFormat.getDateInstance(DateFormat.FULL); //Data COmpleta
    System.out.println("Data brasileira: "+f.format(data));
	*/
}
