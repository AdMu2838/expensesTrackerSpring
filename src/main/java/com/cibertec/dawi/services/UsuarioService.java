package com.cibertec.dawi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.dawi.models.Usuario;
import com.cibertec.dawi.repositories.IUsuarioRepository;

@Service
public class UsuarioService {

	
	private final IUsuarioRepository usuarioRepository;

	@Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> buscarXEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public int actualizar(int id_usuario, String nom_usuario, String email_usuario, String pass_usuario) {
        try {
        	usuarioRepository.actualizar(id_usuario, nom_usuario, email_usuario, pass_usuario);
    		return 1; //1 = Ok
    	}catch (Exception e) {
    		System.out.println(e.getMessage());
			return 0; //0 = Error
		}
    }

    public int agregar(Usuario usuario) {
        try {
        	usuarioRepository.saveAndFlush(usuario);
    		return 1; //1 = Ok
    	}catch (Exception e) {
    		System.out.println(e.getMessage());
			return 0; //0 = Error
		}
    }
}
