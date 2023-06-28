package com.cibertec.dawi.models;

import java.util.List;

public class RegistroReport {

	public List<Registro> registrosList;

	public RegistroReport() {
		super();
	}

	public List<Registro> getRegistrosList() {
		return registrosList;
	}

	public RegistroReport(List<Registro> registrosList) {
		super();
		this.registrosList = registrosList;
	}

	public void setRegistrosList(List<Registro> registrosList) {
		this.registrosList = registrosList;
	}
	
	
}
