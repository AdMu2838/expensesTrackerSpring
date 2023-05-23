package com.cibertec.dawi.controllers;

import com.cibertec.dawi.models.Registro;
import com.cibertec.dawi.services.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroController {

    private final RegistroService registroService;

    @Autowired
    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping
    public ResponseEntity<List<Registro>> obtenerRegistros() {
        List<Registro> registros = registroService.obtenerRegistros();
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registro> obtenerRegistroPorId(@PathVariable String id) {
        Registro registro = registroService.obtenerRegistroPorId(id);
        if (registro != null) {
            return ResponseEntity.ok(registro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Registro> crearRegistro(@RequestBody Registro registro) {
        Registro nuevoRegistro = registroService.crearRegistro(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRegistro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registro> actualizarRegistro(@PathVariable String id, @RequestBody Registro registro) {
        Registro registroActualizado = registroService.actualizarRegistro(id, registro);
        if (registroActualizado != null) {
            return ResponseEntity.ok(registroActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable String id) {
        boolean eliminado = registroService.eliminarRegistro(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}