package com.cibertec.dawi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cibertec.dawi.models.Usuario;
import com.cibertec.dawi.services.UsuarioService;

@Service
public class SecurityUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = userService.buscarXEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));
		
		UserDetails userDetails = User.builder()
				.username(user.getEmail())
				.password("{noop}"+user.getPassword())
				.roles("USER")
				.build();
		
		return userDetails;
	}
 public void createUser(UserDetails user) { 
      userService.agregar(((Usuario) user));
   } 
}
