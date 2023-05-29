package com.cibertec.dawi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.cibertec.dawi.models.Registro;

@Repository
public interface IRegistroRepository extends JpaRepository<Registro, Long>  {
	
	//Lista todos los registros de un usuario en particular
	@Procedure("usp_obtenerRegistrosParaExportar")
	public List<Registro> obtenerRegistrosParaExportar (int codigoUsuario);
	
	//Lista los registros que reflejan ingresos de un usuarios en particular
	public List<Registro> findByImpactoGreaterThan0 (int codigoUsuario);
	
	//Lista los registros que reflejan egresos de un usuarios en particular
	public List<Registro> findByImpactoLessThan0 (int codigoUsuario);
	
	@Procedure("usp_obtenerSumatoriaGeneralImpactosYFecha")
	//Obtiene los impactos (ingresos y egresos) y sus fechas de un Usuario
	public List<List<Registro>> obtenerImpactosYFecha (int codigoUsuario);
	
	//Elimina un registro en particular
	@Procedure("usp_eliminarRegistro")
	public void eliminar (String codigoRegistro, int codigoUsuario);
	
}
