package com.ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufc.br.model.Prato;

public  interface PratoRepository extends JpaRepository<Prato, Long>{
}
