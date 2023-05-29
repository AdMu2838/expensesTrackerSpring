package com.cibertec.dawi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.cibertec.dawi.models.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

	//Busqueda de un usuario especifico mediante email
	public Usuario findByEmail (String email);
	
	//Eliminacion de un usuario especifico mediante codigo
	public void deleteById(int cod);
	
	//Actualizacion de un usuario especifico mediante nuevo objeto de tipo UsuarioDTO
	@Procedure("usp_actualizarUsuario")
	public int actualizar (Usuario usuario);
	
	//Creacion de un nuevo usuario mediante nuevo objeto de tipo UsuarioDTO
	public Usuario agregar (Usuario usuario);

	
	
}
