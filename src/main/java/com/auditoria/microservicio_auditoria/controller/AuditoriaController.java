package com.auditoria.microservicio_auditoria.controller;


import com.auditoria.microservicio_auditoria.dto.AuditoriaRequestDTO;
import com.auditoria.microservicio_auditoria.dto.AuditoriaResponseDTO;
import com.auditoria.microservicio_auditoria.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    public ResponseEntity<List<AuditoriaResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok((auditoriaService.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(auditoriaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AuditoriaResponseDTO> generarAuditoria(@RequestBody AuditoriaRequestDTO auditoria){
        return ResponseEntity.status(HttpStatus.CREATED).body(auditoriaService.generarAuditoria(auditoria));
    }

}
