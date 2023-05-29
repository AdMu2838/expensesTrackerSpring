package com.cibertec.dawi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usu")
    private int id;

    @Column(name = "nom_usu")
    private String nombre;

    @Column(name = "email_usu")
    private String email;

    @Column(name = "pass_usu")
    private String password;

    @Column(name = "presu_usu")
    private double presupuesto;

    @OneToMany(mappedBy = "usuario")
    private List<Registro> registros;

    public Usuario() {}

    public Usuario(int id, String nombre, String email, String password, double presupuesto, List<Registro> registros) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.presupuesto = presupuesto;
        this.registros = registros;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }
}