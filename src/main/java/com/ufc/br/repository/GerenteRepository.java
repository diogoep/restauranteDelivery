package com.ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufc.br.model.Gerente;

public  interface GerenteRepository extends JpaRepository<Gerente, Long>{
	Gerente findByCnpjGerente(String cnpj);
}
