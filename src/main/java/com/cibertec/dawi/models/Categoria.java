package com.cibertec.dawi.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_categorias")
public class Categoria {
	
	public Categoria() {}
	
	public Categoria(String id, String nombre, char tipo, List<Registro> registros) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.registros = registros;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_cat")
	String id;
	
	@Column(name="nom_cat")
	String nombre;
	
	@Column(name="tipo_cat")
	char tipo;

	@OneToMany(mappedBy = "categoria")
	List<Registro> registros;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	
	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
}
