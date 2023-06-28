package com.cibertec.dawi.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.dawi.models.Registro;
import com.cibertec.dawi.models.RegistroReport;
import com.cibertec.dawi.models.Usuario;
import com.cibertec.dawi.repositories.IRegistroRepository;
import com.cibertec.dawi.utils.DataImpactoYFecha;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class RegistroService{
    private final IRegistroRepository registroRepository;

    @Autowired
    public RegistroService(IRegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }
    @Transactional
    public List<Registro> obtenerRegistrosParaExportar(int codigoUsuario) {
        return registroRepository.obtenerRegistrosParaExportar(codigoUsuario);
    }
    @Transactional
    public List<Registro> listarIngresos(int codigoUsuario) {
        return registroRepository.listarIngresos(codigoUsuario);
    }
    @Transactional
    public List<Registro> listarEgresos(int codigoUsuario) {
    	return registroRepository.listarEgresos(codigoUsuario);
    }
    
    @Transactional
    public List<List<DataImpactoYFecha>> obtenerImpactosYFecha(int codigoUsuario) {
        List<DataImpactoYFecha> general = new ArrayList<>();

		for (Object[] row : registroRepository.obtenerSumatoriaGeneralImpactosYFecha(codigoUsuario)) {
			general.add(new DataImpactoYFecha((double)row[0], (Date)row[1]));
		}
        List<DataImpactoYFecha> ingresos = new ArrayList<>();;
        
        for (Object[] row : registroRepository.obtenerSumatoriaIngresoImpactosYFecha(codigoUsuario)) {
			ingresos.add(new DataImpactoYFecha((double)row[0], (Date)row[1]));
		}
        
        List<DataImpactoYFecha> egresos = new ArrayList<>();;
        
        for (Object[] row : registroRepository.obtenerSumatoriaEgresoImpactosYFecha(codigoUsuario)) {
			egresos.add(new DataImpactoYFecha((double)row[0], (Date)row[1]));
		}
        
        return Arrays.asList(general, ingresos, egresos);
    }
    
    
    public int eliminar(String idRegistro, int codigoUsuario) {
        
        try {
        	registroRepository.eliminar(idRegistro, codigoUsuario);
    		return 1; //1 = Ok
    	}catch (Exception e) {
    		System.out.println(e.getMessage());
			return 0; //0 = Error
		}
    }
    
    public int agregar(String descripcion, double impacto, int codCategoria, int codigoUsuario) {
    	try {
    		registroRepository.agregar(descripcion, impacto, codCategoria, codigoUsuario);
    		return 1; //1 = Ok
    	}catch (Exception e) {
    		System.out.println(e.getMessage());
			return 0; //0 = Error
		}
    }
    
    public int actualizar(String idRegistro, String descripcion, double impacto, int codCategoria, int codigoUsuario) {
    	try {
    		registroRepository.actualizar(idRegistro, descripcion, impacto, codCategoria, codigoUsuario);
    		return 1; //1 = Ok
    	}catch (Exception e) {
    		System.out.println(e.getMessage());
			return 0; //0 = Error
		}
    }
    
    @Transactional
    public InputStream getRegistroReport(Usuario user) throws JRException{
    	
    	List<Registro> listaRegistros = this.obtenerRegistrosParaExportar(user.getId());
    	List<RegistroReport> listData = new ArrayList<RegistroReport>();
    	listData.add(new RegistroReport(listaRegistros));
    	
    	JRBeanCollectionDataSource dts = new JRBeanCollectionDataSource(listData);
    	
    	try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("nombreUsuario_path", user.getNombre());
			parameters.put("correoUsuario_path", user.getEmail());
			JasperPrint jPrint = JasperFillManager.fillReport(getClass().getResourceAsStream("/jasper/Reporte_Usuario.jasper"), parameters, dts);
			return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jPrint));
			
		} catch (JRException e) {
			throw e;
		}
    }

}