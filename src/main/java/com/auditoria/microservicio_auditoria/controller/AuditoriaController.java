package com.auditoria.microservicio_auditoria.controller;


import com.auditoria.microservicio_auditoria.assembler.AuditoriaModelAssembler;
import com.auditoria.microservicio_auditoria.dto.AuditoriaRequestDTO;
import com.auditoria.microservicio_auditoria.dto.AuditoriaResponseDTO;
import com.auditoria.microservicio_auditoria.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
@Tag(name = "Auditoria", description = "Operaciones relacionadas con las auditorias")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;
    private final AuditoriaModelAssembler auditoriaAssembler;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Listar auditorias", description = "Listado de auditorias disponibles")
    public ResponseEntity<?> obtenerTodos(){
        List<AuditoriaResponseDTO> auditorias = auditoriaService.obtenerTodos()
                .stream().map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<AuditoriaResponseDTO> collectionModel =  CollectionModel.of(auditorias, linkTo(methodOn(AuditoriaController.class).obtenerTodos()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Busqueda de auditoria", description = "Busqueda de auditoria en especifico")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        AuditoriaResponseDTO auditoria = auditoriaService.buscarPorId(id);
        return ResponseEntity.ok(auditoriaAssembler.toModel(auditoria));
    }

    @PostMapping
    @Operation(summary = "Generar auditoria", description = "Registro de auditoria con su evento correspondiente")
    public ResponseEntity<AuditoriaResponseDTO> generarAuditoria(@RequestBody AuditoriaRequestDTO auditoria){
        AuditoriaResponseDTO auditoria1 = auditoriaService.generarAuditoria(auditoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(auditoriaAssembler.toModel(auditoria1));
    }

}
