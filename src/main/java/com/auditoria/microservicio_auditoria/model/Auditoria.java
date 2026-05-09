package com.auditoria.microservicio_auditoria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "auditoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 5)
    private Long idAuditoria;

    @Column(nullable = false)
    private String detalle;

    @Column(nullable = false)
    private LocalDate fecha;




}
