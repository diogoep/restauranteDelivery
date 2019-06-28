package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufc.br.model.Cliente;
import com.ufc.br.model.Role;
import com.ufc.br.repository.ClienteRepository;
import com.ufc.br.service.ClienteService;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@RequestMapping(value ="/Cliente/formularioCliente", method=RequestMethod.GET)
	public ModelAndView formularioCliente(){
		ModelAndView mv = new ModelAndView("Cliente/FormularioCliente");	
			mv.addObject("cliente", new Cliente());
			return mv;
		}
	@RequestMapping(value ="/Cliente/formularioCliente", method=RequestMethod.POST)	
	public ModelAndView cadastrarCliente(@Validated Cliente cliente, BindingResult result){
		Role role = new Role();
		List<Cliente> clientes = new ArrayList<Cliente>();
		List<Role> roles = new ArrayList<Role>();
		role.setPapel("ROLE_USER");
		clientes.add(cliente);
		roles.add(role);
		role.setClientes(clientes);
		cliente.setRoles(roles);
		clienteService.cadastrarCliente(cliente);
		ModelAndView mv = new ModelAndView("redirect:/listarClientes");
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("Login");
		return mv;
	}

    @RequestMapping(value = "/Cliente/atualizaForm", method = RequestMethod.GET)
    public ModelAndView atualizaCliente(Cliente cliente){

        ModelAndView mv = new ModelAndView("Cliente/AtualizaCliente");

        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;

        Cliente clienteF = clienteRepository.findByCpfCliente(user.getUsername());

        mv.addObject("cliente", clienteService.buscar(clienteF.getCodigoCliente()));
        return mv;
    }

    @RequestMapping(value = "/Cliente/salvarCliente", method = RequestMethod.POST)
    public ModelAndView AtualizarDadosCliente( Cliente cliente) {
		Role role = new Role();
		List<Cliente> clientes = new ArrayList<Cliente>();
		List<Role> roles = new ArrayList<Role>();
		role.setPapel("ROLE_USER");
		clientes.add(cliente);
		roles.add(role);
		role.setClientes(clientes);
		cliente.setRoles(roles);
    	
        /*if(result.hasErrors()) {
            return atualizaCliente(cliente);
        }*/

        clienteService.cadastrarCliente(cliente);
        //attributes.addFlashAttribute("mensagem", "Dados atualizados com sucesso.");
        ModelAndView mv = new ModelAndView("redirect:../listarPratos");
        return mv;
    }
/*
	
	@RequestMapping(value ="/atualizaForm", method=RequestMethod.GET)
	public ModelAndView atualizarForm(Cliente cliente){ 
		ModelAndView mv = new ModelAndView("Cliente/AtualizaCliente");
		mv.addObject("cliente",new Cliente());
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(auth instanceof UserDetails) {
			UserDetails user = (UserDetails) auth;
			Cliente a = clienteRepository.findByCpfCliente(user.getClass().getName());
			cliente.ad
			mv.addObject("cliente", a);
			return mv;
		}else{
			  String username = auth.toString();
			  System.out.print(auth.toString());
			  Cliente b = clienteRepository.findByCpfCliente(username);
			  mv.addObject("cliente", b);
			  return mv;
		}
	}

	
	@RequestMapping(value = "/atualizaForm", method = RequestMethod.POST)
	public ModelAndView salvarCliente(@Validated Cliente cliente, BindingResult result) {
		ModelAndView mv = new ModelAndView("Cliente/AtualizaCliente");
		if(result.hasErrors()) {
			return mv; //retorna pra página de form e nem tenta salvar
		}else {
			clienteService.atualizarCliente(cliente);
			return mv;
		}
	}
	*/
	@GetMapping("/listarClientes")
	public ModelAndView listarClientes() {
		List<Cliente> clientes = clienteService.listarTodosClientes();
		ModelAndView mv = new ModelAndView("Cliente/PaginaListagemCliente");
		mv.addObject("listaClientes", clientes);
		return mv;
	}
	
	@GetMapping("/excluirCliente/{codigoCliente}")
	public ModelAndView removerCliente(@PathVariable Long codigoCliente) {
		clienteService.excluirCliente(codigoCliente);
		ModelAndView mv = new ModelAndView("redirect:/listarClientes");
		return mv;
	}
}