package com.cibertec.dawi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cibertec.dawi.models.Registro;
import com.cibertec.dawi.models.Usuario;
import com.cibertec.dawi.services.RegistroService;
import com.cibertec.dawi.services.UsuarioService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class DashboardController {
    private final RegistroService registroService;
    private final UsuarioService usuarioService;
    private final Gson gson;

    @Autowired
    public DashboardController(RegistroService registroService, UsuarioService usuarioService) {
        this.registroService = registroService;
        this.usuarioService = usuarioService;
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
    }

    @GetMapping("/registros-para-exportar")
    @ResponseBody
    public List<Registro> obtenerRegistrosParaExportar(@RequestParam int codigoUsuario) {
        return registroService.obtenerRegistrosParaExportar(codigoUsuario);
    }

    @GetMapping("/registros-ingresos")
    @ResponseBody
    public List<Registro> listarIngresos(@RequestParam int codigoUsuario) {
        return registroService.listarIngresos(codigoUsuario);
    }

    @GetMapping("/registros-egresos")
    @ResponseBody
    public List<Registro> listarEgresos(@RequestParam int codigoUsuario) {
        return registroService.listarEgresos(codigoUsuario);
    }

    @GetMapping("/impactos-y-fecha")
    @ResponseBody
    public List<List<Registro>> obtenerImpactosYFecha(@RequestParam int codigoUsuario) {
        return registroService.obtenerImpactosYFecha(codigoUsuario);
    }

    @PostMapping("/eliminar-registro")
    @ResponseBody
    public void eliminarRegistro(@RequestParam String codigoRegistro, @RequestParam int codigoUsuario) {
        registroService.eliminar(codigoRegistro, codigoUsuario);
    }

    @PostMapping("/actualizar-usuario")
    @ResponseBody
    public int actualizarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.actualizar(usuario);
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}