package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ufc.br.model.Cliente;
import com.ufc.br.model.Pedido;
import com.ufc.br.repository.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente buscar(long id) {
		if(clienteRepository.getOne(id) != null) {
			return clienteRepository.getOne(id);
		}
		return null;
	}
	
	
	public void atualizarCliente(@RequestBody Cliente cliente) {   
		clienteRepository.save(cliente);
	}
	
	public void cadastrarCliente(Cliente cliente) {
		cliente.setCpfCliente(cliente.getCpfCliente().replaceAll("\\.", "").replaceAll("\\-",""));
		cliente.setSenhaCliente(new BCryptPasswordEncoder().encode(cliente.getSenhaCliente()));
		clienteRepository.save(cliente);
	}
	
	public List<Cliente> listarTodosClientes(){
		return clienteRepository.findAll();
	}
	
	
	public void excluirCliente(Long codigo) {
		clienteRepository.deleteById(codigo);
	}

}