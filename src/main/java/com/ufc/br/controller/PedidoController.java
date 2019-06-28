package com.ufc.br.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.ufc.br.model.Cliente;
import com.ufc.br.model.Item;
import com.ufc.br.model.Pedido;
import com.ufc.br.repository.ClienteRepository;
import com.ufc.br.repository.PedidoRepository;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.service.ClienteService;
import com.ufc.br.service.EnvioEmail;
import com.ufc.br.service.PedidoService;


@Controller
public class PedidoController {

//    @Autowired 
//    private JavaMailSender mailSender;

	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteController clienteController;
	
	@Autowired
	private PratoRepository pratoRepository;
	

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@RequestMapping(value = "/listarPedidos", method = RequestMethod.GET)
	public ModelAndView listarPedidos() {
			ModelAndView mv = new ModelAndView("Pedido/PaginaListagemPedidos");
			Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(auth instanceof UserDetails) {
				UserDetails user = (UserDetails) auth;	
				List<Pedido> pedidos = pedidoService.buscarCpfCliente(user.getUsername());
				mv.addObject("listaPedidos",pedidos);
				return mv;
			}else {
				String user = auth.toString();
				List<Pedido> pedidos = pedidoService.buscarCpfCliente(user);
				mv.addObject("listaPedidos",pedidos);
				return mv;
			}
	
	}
	
	
	@RequestMapping(value = "/listarPedidos/{id}", method = RequestMethod.GET)
	public ModelAndView listarPedidos(@PathVariable("id") Long id) {
			ModelAndView mv = new ModelAndView("Pedido/PaginaListagemPedidosCliente");
			Cliente cliente = clienteRepository.getOne(id);
			List<Pedido> pedidos = pedidoService.buscarCpfCliente(cliente.getCpfCliente());
			mv.addObject("listaPedidos", pedidos);
			return mv;
	
	}
	
	@RequestMapping(value = "/listarPedido/{id}", method = RequestMethod.GET)
	public ModelAndView listarPedido(@PathVariable("id") Long id) {
			ModelAndView mv = new ModelAndView("Pedido/PaginaItensPedido");
			Pedido pedido = pedidoRepository.getOne(id);
			List<Item> itens = pedido.getItens(); 
			mv.addObject("itensPedido", itens);
			return mv;
	}
	
	@RequestMapping(value = "/visualizarPedido", method = RequestMethod.GET)
	public String listarPratosPedido() {
		return "Pedido/PaginaListagemItens";
	}
	
	@RequestMapping(value = "/adicionarItem/{id}", method = RequestMethod.GET)
	public ModelAndView adicionarItem(@PathVariable("id") Long id, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("redirect:/visualizarPedido");
        if(session.getAttribute("pedido") == null){
			List<Item> pedido = new ArrayList<Item>();
			Item i = new Item(pratoRepository.getOne(id), 1);
			//throw new Exception(""+ i.getCodigo());
			pedido.add(i);
			session.setAttribute("pedido", pedido);
			mv.addObject("pedido", pedido);
		} else {
			List<Item> pedido = (List<Item>) session.getAttribute("pedido");
			int index = exists(id, pedido);
			if(index == -1){
				pedido.add(new Item(pratoRepository.getOne(id), 1));
				session.setAttribute("pedido", pedido);
				mv.addObject("pedido",pedido);
			}else{
				int quantidade = pedido.get(index).getQuantidade() + 1;
				pedido.get(index).setQuantidade(quantidade);
				session.setAttribute("pedido", pedido);
				mv.addObject("pedido", pedido);
			}
		}
		return mv;
	}

	public int exists(Long id, List<Item> pedido) throws Exception{
		for(int i = 0; i < pedido.size(); i++){
			if(((pedido.get(i).getPrato().getCodigoPrato()).equals(id))){
				return i;
			}
		}
		return -1;
	}
	
	@RequestMapping(value = "/removerItem/{id}", method = RequestMethod.GET)
	public ModelAndView removerItem(@PathVariable("id") Long id, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("redirect:/visualizarPedido");
		List<Item> pedido = (List<Item>) session.getAttribute("pedido");
		int index = this.exists(id, pedido);
		if(pedido.get(index).getQuantidade() == 1) {
			pedido.remove(index);
		}else{
			int quantidade = pedido.get(index).getQuantidade() - 1;
			pedido.get(index).setQuantidade(quantidade);
		}
		session.setAttribute("pedido", pedido);
		return mv;
	}

	

	@RequestMapping(value = "/salvarPedido", method = RequestMethod.GET)
	public String salvarPedido(HttpSession session) {
		float valorTotal = 0;
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		if(session.getAttribute("pedido") != null) {
			List<Item> pedido = (List<Item>) session.getAttribute("pedido");
			Pedido pedidoFinal = new Pedido();
			ArrayList<Item> itens = new ArrayList<>();
			for(int i=0; i<pedido.size(); i++) {
				Item item = new Item();
				item.setPrato(pedido.get(i).getPrato());
				item.setQuantidade(pedido.get(i).getQuantidade());
				pedidoService.salvarItem(item);
				itens.add(item);
			}
			/*
			if (itens.size() == 0) {
				return "";
			}*/
			pedidoFinal.setItens(itens);
			Calendar c = Calendar.getInstance();
		    Date data = c.getTime();
		    //System.out.println("Data brasileira: "+f.format(data));
			pedidoFinal.setDataConfirmacao(data);
			for(Item item: pedido) {
				valorTotal = ((float) (item.getQuantidade())) * ((float) (item.getPrato().getPrecoPrato().floatValue()));
			}
			pedidoFinal.setDataPedidoAceito(null);
			pedidoFinal.setValorCompra(valorTotal);
			//pedidoFinal.setDataPedidoAceito(data);
			Cliente cliente = clienteRepository.findByCpfCliente(user.getUsername());
			pedidoFinal.setCliente(cliente);
			//EnvioEmail envio = new EnvioEmail();
			//envio.sendMail();
			//envio.enviar("diogo.eliseu.2951@hotmail.com", "teste assunto", "teste email");
			pedidoService.salvarPedido(pedidoFinal);
			session.removeAttribute("pedido");
			return "redirect:/listarPratos";
		}else{
			session.removeAttribute("pedido");
			return "redirect:/listarPratos";
		}
		
	}

}
/*
@Controller
public class PedidoController{
	@Autowired
	private PratoRepository pratoRepository;
	
	@GetMapping("/adicionarPrato/{codigoP}")
	public ModelAndView cadastrarPratoPedido(@PathVariable Long codigoP) {
		Pedido pedido = new Pedido();
		Date data = new Date();
		Prato prato = pratoRepository.getOne(codigoP);
		pedido.setValorCompra(pedido.getValorCompra().add(prato.getPrecoPrato()));
		pedido.getPratos().add(prato);
		pedido.setData(data);
		
		ModelAndView mv = new ModelAndView("redirect:/listarItensPedido/");
		mv.addObject("itensPedido", pedido);
		return mv;
	}
	
}


*/