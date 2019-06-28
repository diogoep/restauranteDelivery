package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufc.br.model.Prato;
import com.ufc.br.model.Role;
import com.ufc.br.repository.GerenteRepository;
import com.ufc.br.repository.PedidoRepository;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.model.Gerente;
import com.ufc.br.model.Pedido;
import com.ufc.br.service.GerenteService;
@Controller
public class GerenteController {
	@Autowired
	private GerenteService gerenteService;
	@Autowired
	private GerenteRepository gerenteRepository;
	
	@Autowired
	private PratoRepository pratoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@RequestMapping("/formularioPrato")
	public String formularioPrato() {
		return "Prato/FormularioPrato";
	}
	
	@RequestMapping(value ="/Gerente/formularioGerente", method=RequestMethod.GET)
	public ModelAndView formularioGerente(Gerente gerente){
		ModelAndView mv = new ModelAndView("Gerente/FormularioGerente");	
			mv.addObject("gerente", new Gerente());
			return mv;
		}
	
	
	@RequestMapping(value ="/Gerente/listarPedidos", method=RequestMethod.GET)
	public ModelAndView listarPedidos(){
		ModelAndView mv = new ModelAndView("Gerente/PaginaListagemPedidos");	
		List<Pedido> pedidos = pedidoRepository.findAll();
			mv.addObject("listaPedidos", pedidos);
			return mv;
		}
	

	@RequestMapping(value ="/Gerente/listarTodosPedidos", method=RequestMethod.GET)
	public ModelAndView listarTodosPedidos(){
		ModelAndView mv = new ModelAndView("Gerente/PaginaListagemTodosPedidos");	
		List<Pedido> pedidos = pedidoRepository.findAll();
			mv.addObject("listaPedidos", pedidos);
			return mv;
		}
	

	@RequestMapping(value ="/aceitarPedido/{id}", method=RequestMethod.GET)
	public ModelAndView listarPedidos(@PathVariable ("id") Long id){
		ModelAndView mv = new ModelAndView("redirect:/Gerente/listarPedidos");	
		Pedido pedido = pedidoRepository.getOne(id);
		Calendar c = Calendar.getInstance();
		Date data = c.getTime();
		pedido.setDataPedidoAceito(data);
		pedidoRepository.save(pedido);
		mv.addObject("listaPedidos", pedido);
		return mv;
	}
	
	@RequestMapping(value ="/Gerente/formularioGerente", method=RequestMethod.POST)	
	public ModelAndView cadastrarGerente(Gerente gerente){
		Role role = new Role();
		List<Gerente> gerentes = new ArrayList<Gerente>();
		List<Role> roles = new ArrayList<Role>();
		role.setPapel("ROLE_ADMIN");
		gerentes.add(gerente);
		roles.add(role);
		role.setGerentes(gerentes);
		gerente.setRoles(roles);
		gerenteService.cadastrarGerente(gerente);
		ModelAndView mv = new ModelAndView("redirect:/listarGerentes");
		return mv;
	}
	

    @RequestMapping(value = "/Gerente/atualizaForm", method = RequestMethod.GET)
    public ModelAndView atualizaGerente(Gerente gerente) {
        ModelAndView mv = new ModelAndView("Gerente/AtualizaGerente");
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;
        Gerente gerenteF = gerenteRepository.findByCnpjGerente(user.getUsername());
        mv.addObject("gerente", gerenteService.busca(gerenteF.getCodigoGerente()));
        return mv;
    }

    @RequestMapping(value = "/Gerente/salvarGerente", method = RequestMethod.POST)
    public ModelAndView atualizaDadosGerente(Gerente gerente) {
		Role role = new Role();
		List<Gerente> gerentes = new ArrayList<Gerente>();
		List<Role> roles = new ArrayList<Role>();
		role.setPapel("ROLE_ADMIN");
		gerentes.add(gerente);
		roles.add(role);
		role.setGerentes(gerentes);
		gerente.setRoles(roles);
        gerenteService.cadastrarGerente(gerente);
        ModelAndView mv = new ModelAndView("redirect:/listarPratos");
        return mv;
    }
    
	@PostMapping("/formularioPrato")
	public ModelAndView salvarPrato(Prato prato, @RequestParam (value="imagemPrato") MultipartFile imagem){
		gerenteService.cadastrarPrato(prato, imagem);
		ModelAndView mv = new ModelAndView("redirect:/listarPratos") ;
		return mv;
	}
	
	@GetMapping("/listarPratos")
	public ModelAndView listarPratos() {
		List<Prato> pratos = gerenteService.listarPratos();
		ModelAndView mv = new ModelAndView("Prato/PaginaListagemPrato");
		mv.addObject("listaPratos", pratos);
		return mv;
	}
	
	@RequestMapping("/excluirPrato/{codigoP}")
	public ModelAndView deletarPrato(@PathVariable (value = "codigoP") Long prato){
		Prato p = pratoRepository.getOne(prato);
		p.setAtivo(false);
		pratoRepository.save(p);
		ModelAndView mv = new ModelAndView("redirect:/listarPratos");
		return mv;
	}

	
	@GetMapping("/listarGerentes")
	public ModelAndView listarGerentes() {
		List<Gerente> gerentes = gerenteService.listarTodosGerentes();
		ModelAndView mv = new ModelAndView("Gerente/PaginaListagemGerente");
		mv.addObject("listaGerentes", gerentes);
		return mv;
	}
	
	@RequestMapping("/excluirGerente/{codigo}")
	public ModelAndView deletar(@PathVariable Long codigo){
		gerenteService.excluirGerente(codigo);
		ModelAndView mv = new ModelAndView("redirect:/listarGerentes");
		return mv;
	}
	
}
