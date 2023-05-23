package com.cibertec.dawi.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_registro")
public class Registro {

	public Registro() {}
	
	public Registro(String id, String descripcion, Categoria categoria, double impacto, Date fecha, Usuario usuario) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.categoria = categoria;
		this.impacto = impacto;
		this.fecha = fecha;
		this.usuario = usuario;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_reg")
	String id;
	
	@Column(name="desc_reg")
	String descripcion;
	
	@ManyToOne
	@JoinColumn(name="cod_cat")
	Categoria categoria;
	
	@Column(name="impac_reg")
	double impacto;
	
	@Column(name="fec_reg")
	Date fecha;
	
	@ManyToOne
	@JoinColumn(name="id_usu")
	Usuario usuario;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public double getImpacto() {
		return impacto;
	}

	public void setImpacto(double impacto) {
		this.impacto = impacto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}