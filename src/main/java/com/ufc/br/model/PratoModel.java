package com.ufc.br.model;

import java.util.ArrayList;
import java.util.List;
import com.ufc.br.repository.PratoRepository;

public class PratoModel {

	private PratoRepository pratoRepository;
	
	private List<Prato> pratos;
		
	public PratoModel() {
		this.pratos = new ArrayList<Prato>();
		this.pratos = pratoRepository.findAll();
	}
	
	public List<Prato> findAll() {
		return this.pratos;
	}

	public Prato find(Long id) {
		for (Prato prato : this.pratos) {
			if (prato.getCodigoPrato() == id) {
				return prato;
			}
		}
		return null;
	}

}
