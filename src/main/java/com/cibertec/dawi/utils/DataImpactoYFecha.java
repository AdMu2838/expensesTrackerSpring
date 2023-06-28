package com.cibertec.dawi.utils;

import java.util.Date;

import javax.persistence.Column;

public class DataImpactoYFecha {
	@Column(name = "impac_reg")
	private double impacto;
	@Column(name = "fec_reg")
    private Date fecha;

    public DataImpactoYFecha() {
    	super();
    }
    public DataImpactoYFecha(double impacto, Date fecha) {
        this.impacto = impacto;
        this.fecha = fecha;
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
}
