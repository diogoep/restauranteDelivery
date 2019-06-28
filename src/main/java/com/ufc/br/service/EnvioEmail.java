package com.ufc.br.service;


import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Cliente;
import com.ufc.br.repository.ClienteRepository;

@Service
public class EnvioEmail {
 
    @Autowired
    private JavaMailSender mailSender;
 
    @Autowired
    private ClienteRepository clienteRepository;

    public String sendMail() {
        try {
    		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper( mail );
    		if(auth instanceof UserDetails) {
				UserDetails user = (UserDetails) auth;	
				Cliente cliente = clienteRepository.findByCpfCliente(user.getUsername());
				String email = cliente.getEmailCliente();
	            helper.setTo(email);
			}else {
				String user = auth.toString();
				Cliente cliente = clienteRepository.findByCpfCliente(user);
				String email = cliente.getEmailCliente();
				helper.setTo(email);
			}
            helper.setSubject( "Teste Envio de e-mail" );
            helper.setText("<p>Hello from Spring Boot Application</p>", true);
            mailSender.send(mail);
            //EnvioEmail envio = new EnvioEmail();
            //envio.enviar("diogo.eliseu.2951@gmail.com","a","teste");
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar e-mail";
        }
    }
    
    public void enviar(String email, String assunto, String mensagem) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject(assunto);
        mail.setText(mensagem);
        mailSender.send(mail);
    }
}