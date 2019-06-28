package com.ufc.br.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.ufc.br.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsServiceImplementacao userDetailsServiceImplementacao;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.csrf().disable().authorizeRequests()
	  
	  //Páginas que todos podem acessar :)
	  .antMatchers("/formularioCliente").permitAll()
	  .antMatchers("/listarPratos").permitAll()
	  .antMatchers("/salvarCliente").permitAll()
	  .antMatchers("/formularioGerente").permitAll()
	  .antMatchers("/salvarGerente").permitAll()
	  .antMatchers("/Cliente/formularioCliente").permitAll()
	  //Páginas que precisam de autenticação :)
	  .anyRequest().authenticated()
	  
	  //Definindo página de login :)
	  .and()
	  .formLogin().loginPage("/login").defaultSuccessUrl("/listarPratos")
	  .permitAll()
	  
	  //Definindo página de logout :)
	  .and()
	  .logout().invalidateHttpSession(true).clearAuthentication(true).logoutUrl("/logout")
	  .logoutSuccessUrl("/login")
	  .permitAll();
	}
	
	//método de autenicação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	   auth.userDetailsService(userDetailsServiceImplementacao).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
	}

	
}
