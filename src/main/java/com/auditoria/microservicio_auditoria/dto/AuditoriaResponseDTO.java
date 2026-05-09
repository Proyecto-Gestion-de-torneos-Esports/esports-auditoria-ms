package com.auditoria.microservicio_auditoria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriaResponseDTO {

    private String detalle;
    private LocalDate fecha;


}
