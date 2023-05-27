package com.cibertec.dawi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.dawi.models.Registro;
import com.cibertec.dawi.repositories.IRegistroRepository;

@Service
public class RegistroService{
    private final IRegistroRepository registroRepository;

    @Autowired
    public RegistroService(IRegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    public List<Registro> obtenerRegistrosParaExportar(int codigoUsuario) {
        return registroRepository.obtenerRegistrosParaExportar(codigoUsuario);
    }

    public List<Registro> listarIngresos(int codigoUsuario) {
        return registroRepository.findByImpactoGreaterThan0(codigoUsuario);
    }

    public List<Registro> listarEgresos(int codigoUsuario) {
        return registroRepository.findByImpactoLessThan0(codigoUsuario);
    }

    public List<List<Registro>> obtenerImpactosYFecha(int codigoUsuario) {
        return registroRepository.obtenerImpactosYFecha(codigoUsuario);
    }

    public void eliminar(String codigoRegistro, int codigoUsuario) {
        registroRepository.eliminar(codigoRegistro, codigoUsuario);
    }

}