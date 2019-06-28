package com.ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ufc.br.model.Gerente;
import com.ufc.br.model.Prato;
import com.ufc.br.repository.GerenteRepository;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.util.AulaFileUtils;

import java.util.List;

@Service
public class GerenteService {
	@Autowired
	private GerenteRepository gerenteRepository;
	
	@Autowired
	private PratoRepository pratoRepository;
	
	public void cadastrarPrato(Prato prato, MultipartFile imagem){
		String[] extensao = imagem.getOriginalFilename().split("\\.(?=[^\\.]+$)");
		prato.setExtensaoImagemPrato(extensao[1]);
		String caminho = "images/" + prato.getNomePrato() +"."+ extensao[1];
		AulaFileUtils.salvarImagemPrato(caminho,imagem);
		pratoRepository.save(prato);
	}
	
	public List<Prato> listarPratos(){
		return pratoRepository.findAll();
	}
	
	public void cadastrarGerente(Gerente gerente) {
		gerente.setCnpjGerente(gerente.getCnpjGerente().replaceAll("\\.", "").replaceAll("\\-","").replaceAll("\\/", ""));
		gerente.setSenhaGerente(new BCryptPasswordEncoder().encode(gerente.getSenhaGerente()));
		gerenteRepository.save(gerente);
		
	}
	
	public List<Gerente> listarTodosGerentes(){
		return gerenteRepository.findAll();
	}
	
	public Gerente busca(long id) {
		if(gerenteRepository.getOne(id)!= null) {
			return gerenteRepository.getOne(id);
		}
		return null;
	}
	public void excluirGerente(Long codigo) {
		gerenteRepository.deleteById(codigo);
	}
	
	public void excluirPrato(Long codigoPrato) {
		pratoRepository.deleteById(codigoPrato);
	}
}
