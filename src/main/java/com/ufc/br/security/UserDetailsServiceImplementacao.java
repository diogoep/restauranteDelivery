package com.ufc.br.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ufc.br.repository.ClienteRepository;
import com.ufc.br.repository.GerenteRepository;
import com.ufc.br.model.Cliente;
import com.ufc.br.model.Gerente;

@Repository
public class UserDetailsServiceImplementacao implements UserDetailsService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private GerenteRepository gerenteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException{
		Cliente cliente = clienteRepository.findByCpfCliente(cpf);
		if(cliente == null) {
			Gerente gerente = gerenteRepository.findByCnpjGerente(cpf);
			if(gerente == null) {
				throw new UsernameNotFoundException("erro");
			}else{
				return gerente;
			}
		}

		return cliente;
	}
	

	
}
