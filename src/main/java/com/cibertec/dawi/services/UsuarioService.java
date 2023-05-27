package com.cibertec.dawi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.dawi.models.Usuario;
import com.cibertec.dawi.repositories.IUsuarioRepository;
@Service
public class UsuarioService {

	private final IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario buscarXEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void eliminar(int cod) {
        usuarioRepository.deleteById(cod);
    }

    public int actualizar(Usuario usuario) {
        return usuarioRepository.actualizar(usuario);
    }

    public Usuario agregar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
