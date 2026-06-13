package com.auditoria.microservicio_auditoria.assembler;

import com.auditoria.microservicio_auditoria.controller.AuditoriaController;
import com.auditoria.microservicio_auditoria.dto.AuditoriaResponseDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuditoriaModelAssembler implements RepresentationModelAssembler<AuditoriaResponseDTO, AuditoriaResponseDTO> {

    @Override
    public AuditoriaResponseDTO toModel(AuditoriaResponseDTO auditoria){

        auditoria.add(linkTo(methodOn(AuditoriaController.class).obtenerTodos()).withRel("auditorias"));
        auditoria.add(linkTo(methodOn(AuditoriaController.class).buscarPorId(auditoria.getIdAuditoria())).withSelfRel());
        return auditoria;
    }


}
