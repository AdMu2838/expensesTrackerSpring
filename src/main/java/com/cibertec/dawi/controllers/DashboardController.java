package com.cibertec.dawi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.dawi.models.Registro;
import com.cibertec.dawi.models.Usuario;
import com.cibertec.dawi.services.RegistroService;
import com.cibertec.dawi.services.UsuarioService;
import com.cibertec.dawi.utils.DataImpactoYFecha;
import com.cibertec.dawi.utils.RegistroLegacySerializer;
import com.cibertec.dawi.utils.RegistroSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.jasperreports.engine.JRException;

@Controller
public class DashboardController {
    private final RegistroService registroService;
    private final UsuarioService usuarioService;
    private final Gson gsonRegistro;
    private final Gson gsonRegistroLegacy;
    private final Gson gsonImpactoAndFecha;

    @Autowired
    public DashboardController(RegistroService registroService, UsuarioService usuarioService) {
        this.registroService = registroService;
        this.usuarioService = usuarioService;
        this.gsonRegistro = new GsonBuilder().registerTypeAdapter(Registro.class, new RegistroSerializer()).create();
        this.gsonRegistroLegacy = new GsonBuilder().registerTypeAdapter(Registro.class, new RegistroLegacySerializer()).create();
        this.gsonImpactoAndFecha = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
    	//UserDetails loggedUser = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
    	model.addAttribute("loggedUser",loggedUser);
    	model.addAttribute("profitLogs", registroService.listarIngresos(loggedUser.getId()));
    	model.addAttribute("expenseLogs", registroService.listarEgresos(loggedUser.getId()));
        return "dashboard";
    }

    @GetMapping("/dashboard/export_logs_csv")
    public void obtenerRegistrosParaExportar(HttpServletResponse response, Principal principal) throws IOException {
    	Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
        List<Registro> registros = registroService.obtenerRegistrosParaExportar(loggedUser.getId());
        String data = gsonRegistro.toJson(registros);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(data);
    }

    @GetMapping("/dashboard/presupuesto")
    public void obtenerPresupuesto(HttpServletResponse response, Principal principal) throws IOException {
    	Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
    	response.getWriter().write(String.valueOf(loggedUser.getPresupuesto()));
    }

    
    @GetMapping("/dashboard/impactos_y_fecha")
    public void obtenerImpactosYFecha(HttpServletResponse response, Principal principal) throws IOException {
    	Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
    	
    	List<List<DataImpactoYFecha>> impactos = registroService.obtenerImpactosYFecha(loggedUser.getId());
    	String data = gsonImpactoAndFecha.toJson(impactos);
    	response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(data);
    }
    
    @GetMapping("/dashboard/registro_report_pdf")
    public ResponseEntity<ByteArrayResource> generateProductPdf(Principal principal) throws IOException, JRException{
    	Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
		InputStream pdfStream = this.registroService.getRegistroReport(loggedUser);
		byte[] data = pdfStream.readAllBytes();
	    pdfStream.close();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_registros.pdf");
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentLength(data.length);
       
        // Return the HTML content as a response
        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(data));
	}
    
    @GetMapping("/dashboard/queryRegIngresos")
    private void queryRegIngresos(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
		
    	Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
		List<Registro> lista = registroService.listarIngresos(loggedUser.getId());
		String json = gsonRegistroLegacy.toJson(lista);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		
	}
    
    @GetMapping("/dashboard/queryRegEgresos")
    private void queryRegEgresos(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
		
    	Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
		List<Registro> lista = registroService.listarEgresos(loggedUser.getId());
		String json = gsonRegistroLegacy.toJson(lista);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		
	}
    
    @PostMapping("/dashboard/agregar_registro")
    public void agregarRegistro(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
    	
		String descripcion = request.getParameter("reg_descripcion");
		int codCategoria = Integer.parseInt(request.getParameter("reg_cod_categoria"));
		double impacto = Double.parseDouble(request.getParameter("reg_impacto"));
		if(request.getParameter("reg_tipo").equals("egreso")) {
			impacto = Math.negateExact((long)impacto);
		}
		Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
		
		int codigoUsuario = (loggedUser.getId());
		int estado = registroService.agregar(descripcion, impacto, codCategoria, codigoUsuario);

        response.getWriter().append(""+estado);
    }
    
    @PostMapping("/dashboard/actualizar_registro")
    public void actualizarRegistro(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
    	
    	String id = request.getParameter("reg_id");
		String descripcion = request.getParameter("reg_descripcion");
		int codCategoria = Integer.parseInt(request.getParameter("reg_cod_categoria"));
		double impacto = Double.parseDouble(request.getParameter("reg_impacto"));
		if(request.getParameter("reg_tipo").equals("egreso")) {
			impacto = Math.negateExact((long)impacto);
		}
		Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
		
		int codigoUsuario = (loggedUser.getId());
		int estado = registroService.actualizar(id, descripcion, impacto, codCategoria, codigoUsuario);

        response.getWriter().append(""+estado);
    }
    
    @PostMapping("/dashboard/eliminar_registro")
    public void eliminarRegistro(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
    	
    	String id = request.getParameter("reg_id");
		Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
		int codigoUsuario = (loggedUser.getId());
		int estado = registroService.eliminar(id, codigoUsuario);

        response.getWriter().append(""+estado);
    }
    
    @PostMapping("/dashboard/actualizar_usuario")
    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException, ServletException {
		
    	Usuario loggedUser = usuarioService.buscarXEmail(principal.getName()).orElseThrow();
		int codigoUsuario = (loggedUser.getId());
		
		String nombre = request.getParameter("usu_nombre");
		String email = request.getParameter("usu_email");
		String password = request.getParameter("usu_pass");
		
		int estado = usuarioService.actualizar(codigoUsuario, nombre, email, password);
		response.getWriter().write(""+estado);
	}
}