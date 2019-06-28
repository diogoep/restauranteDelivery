package com.ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufc.br.model.Cliente;

public  interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Cliente findByCpfCliente(String username);
	
}
