package com.ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Cliente;
import com.ufc.br.model.Item;
import com.ufc.br.model.Pedido;
import com.ufc.br.repository.ClienteRepository;
import com.ufc.br.repository.ItemRepository;
import com.ufc.br.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ClienteRepository clienteRepository;

	public List<Pedido> buscarCpfCliente(String cpf) {
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<Pedido> pedidosCliente = new ArrayList<Pedido>();
		for(Pedido pedido: pedidos) {
			if(pedido.getCliente().getCpfCliente().equals(cpf)) {
				pedidosCliente.add(pedido);
			}
		}
		return pedidosCliente;
	}
	
	public void salvarItem(Item item){
		itemRepository.save(item);
	}
	
	public void salvarPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
	}
}
