package com.cibertec.dawi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.dawi.models.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

	//Busqueda de un usuario especifico mediante email
	public Optional<Usuario> findByEmail (String email);
	
	//Actualizacion de un usuario especifico mediante nuevo objeto de tipo UsuarioDTO
	@Procedure("usp_actualizarUsuario")
	public void actualizar (int id_usuario, String nom_usuario, String email_usuario, String pass_usuario);
	
}