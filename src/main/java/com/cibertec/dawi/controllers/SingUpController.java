package com.cibertec.dawi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.dawi.models.Usuario;
import com.cibertec.dawi.services.UsuarioService;

@Controller
public class SingUpController {

    private final UsuarioService usuarioService;

    @Autowired
    public SingUpController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        String email = usuario.getEmail();

        if (!validar(email)) {
            redirectAttributes.addFlashAttribute("errorMsj", "¡El correo ya se encuentra registrado!");
            return "redirect:/signup";
        }

        Usuario user = usuarioService.agregar(usuario);

        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMsj", "No se pudo crear el usuario :c");
            return "redirect:/signup";
        }

        return "redirect:/login?src=signup";
    }

    private boolean validar(String email) {
        Usuario user = usuarioService.buscarXEmail(email);
        return user == null;
    }

}