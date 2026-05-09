package com.auditoria.microservicio_auditoria.controller;


import com.auditoria.microservicio_auditoria.dto.AuditoriaRequestDTO;
import com.auditoria.microservicio_auditoria.dto.AuditoriaResponseDTO;
import com.auditoria.microservicio_auditoria.model.Auditoria;
import com.auditoria.microservicio_auditoria.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    public List<AuditoriaResponseDTO> obtenerTodos(){
        return auditoriaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<AuditoriaResponseDTO> buscarPorId(@PathVariable Long id){
        return auditoriaService.buscarPorId(id);
    }

    @PostMapping
    public AuditoriaResponseDTO generarAuditoria(@RequestBody AuditoriaRequestDTO auditoria){
        return auditoriaService.generarAuditoria(auditoria);
    }

}
