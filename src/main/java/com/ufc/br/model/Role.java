package com.ufc.br.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import com.ufc.br.model.Cliente;
import com.ufc.br.model.Gerente;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
@Entity
public class Role implements GrantedAuthority{

	@Id
	private String papel;
	
	@ManyToMany(mappedBy = "roles")
	private List<Cliente> clientes;
	
	@ManyToMany(mappedBy = "roles")
	private List<Gerente> gerentes;
	
	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public List<Gerente> getGerentes() {
		return gerentes;
	}

	public void setGerentes(List<Gerente> gerentes) {
		this.gerentes = gerentes;
	}

	@Override
	public String getAuthority() {
		return this.papel;
	}
	
}