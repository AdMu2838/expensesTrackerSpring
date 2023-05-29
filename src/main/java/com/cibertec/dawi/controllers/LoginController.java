package com.cibertec.dawi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.dawi.models.Usuario;
import com.cibertec.dawi.services.UsuarioService;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    @Autowired
    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String validar(@RequestParam("email") String email,
                          @RequestParam("password") String password,
                          RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.buscarXEmail(email);

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("errorMsj", "No existe tal Email");
            return "redirect:/login";
        } else if (!usuario.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("errorMsj", "Contraseña incorrecta");
            return "redirect:/login";
        }
        
        //Posible codigo para guardar el usuario en una bvariable de sesión u otra forma de autenticación
        
        //Consultar si usaremos Spring Security

        return "redirect:/dashboard";
    }
}