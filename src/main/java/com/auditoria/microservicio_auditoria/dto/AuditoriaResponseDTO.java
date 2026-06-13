package com.auditoria.microservicio_auditoria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriaResponseDTO extends RepresentationModel<AuditoriaResponseDTO> {

    private Long idAuditoria;
    private String detalle;
    private LocalDate fecha;


}
