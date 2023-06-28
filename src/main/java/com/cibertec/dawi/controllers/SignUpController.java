package com.cibertec.dawi.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.dawi.models.Usuario;
import com.cibertec.dawi.services.UsuarioService;

@Controller
public class SignUpController {

    private final UsuarioService usuarioService;

    @Autowired
    public SignUpController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/signup")
    public String signUpView(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(Model model, HttpServletRequest request, HttpServletResponse response) {
    	String email = request.getParameter("email");
		
		if (emailDisponible(email) == false) {
			model.addAttribute("errorMsj", "¡El correo ya se encuentra registrado!");
			return "signup";
		}
		
		String nombre = request.getParameter("nombre");
		String password = request.getParameter("password");
		double presupuesto = Double.parseDouble(request.getParameter("presupuesto"));
		
		Usuario user = new Usuario();
		user.setNombre(nombre);
		user.setEmail(email);
		user.setPassword(password);
		user.setPresupuesto(presupuesto);
		
		int state = usuarioService.agregar(user);
		
		if (state == 0) {
			model.addAttribute("errorMsj", "No se pudo crear el usuario");
			return "signup";
		};
		return "redirect:/login";
		
    }

    private boolean emailDisponible(String email) {
        return usuarioService.buscarXEmail(email).isEmpty();
    }

}
