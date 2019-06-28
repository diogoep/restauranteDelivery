package com.ufc.br.repository;
import com.ufc.br.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ufc.br.model.Cliente;
import com.ufc.br.model.Pedido;

public  interface PedidoRepository extends JpaRepository<Pedido, Long>{
}
