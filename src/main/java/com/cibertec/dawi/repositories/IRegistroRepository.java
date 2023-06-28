package com.cibertec.dawi.repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.dawi.models.Registro;
import com.cibertec.dawi.utils.DataImpactoYFecha;

@Repository
public interface IRegistroRepository extends JpaRepository<Registro, Long>  {
	
	//Lista todos los registros de un usuario en particular
	@Transactional
	@Procedure("usp_obtenerRegistrosParaExportar")
	public List<Registro> obtenerRegistrosParaExportar (int codigoUsuario);
	
	@Transactional
	@Procedure("usp_listarRegistrosIngresos")
	//Lista los registros que reflejan ingresos de un usuarios en particular
	public List<Registro> listarIngresos (int codigoUsuario);
	
	@Transactional
	@Procedure("usp_listarRegistrosEgresos")
	//Lista los registros que reflejan egresos de un usuarios en particular
	public List<Registro> listarEgresos (int codigoUsuario);
	
	@Transactional
	@Procedure("usp_obtenerSumatoriaGeneralImpactosYFecha")
	//Obtiene los impactos (ingresos y egresos) y sus fechas de un Usuario
	public List<Object[]> obtenerSumatoriaGeneralImpactosYFecha (int codigoUsuario);
	
	@Transactional
	@Procedure("usp_obtenerSumatoriaIngresoImpactosYFecha")
	//Obtiene los impactos (ingresos y egresos) y sus fechas de un Usuario
	public List<Object[]> obtenerSumatoriaIngresoImpactosYFecha (int codigoUsuario);
	
	@Transactional
	@Procedure("usp_obtenerSumatoriaEgresoImpactosYFecha")
	//Obtiene los impactos (ingresos y egresos) y sus fechas de un Usuario
	public List<Object[]> obtenerSumatoriaEgresoImpactosYFecha (int codigoUsuario);
	
	@Procedure("usp_agregarRegistro")
	public void agregar (String desc_reg, double impac_reg, int cod_cat, int id_usu);
	
	@Procedure("usp_actualizarRegistro")
	public void actualizar (String id_registro, String desc_registro, double impac_registro, int cod_categoria, int id_usuario);
	
	//Elimina un registro en particular
	@Procedure("usp_eliminarRegistro")
	public void eliminar (String id_registro, int id_usuario);
	
}