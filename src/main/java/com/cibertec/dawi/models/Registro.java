package com.cibertec.dawi.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name="tb_registro")
public class Registro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id_reg")
    private String id;

    @Column(name = "desc_reg", nullable = false)
    private String descripcion;

    @Column(name = "impac_reg", nullable = false)
    private double impacto;

    @Column(name = "fec_reg", nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "cod_cat")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_usu")
    private Usuario usuario;

    private transient SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
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

    public double getImpacto() {
        return impacto;
    }

    public void setImpacto(double impacto) {
        this.impacto = impacto;
    }

    public Date getFecha() {
        return fecha;
    }
    
    public String getFormattedFecha() {
    	return formatter.format(fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}