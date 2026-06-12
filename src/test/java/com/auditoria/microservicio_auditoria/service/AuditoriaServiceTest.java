package com.auditoria.microservicio_auditoria.service;

import com.auditoria.microservicio_auditoria.dto.AuditoriaRequestDTO;
import com.auditoria.microservicio_auditoria.dto.AuditoriaResponseDTO;
import com.auditoria.microservicio_auditoria.model.Auditoria;
import com.auditoria.microservicio_auditoria.repository.AuditoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditoriaServiceTest {

    @Mock
    AuditoriaRepository auditoriaRepository;

    @InjectMocks
    AuditoriaService auditoriaService;

    private Auditoria auditoria;

    @BeforeEach
    void setUp(){
        auditoria = new Auditoria();
        auditoria.setIdAuditoria(1L);
        auditoria.setDetalle("Se creo un evento");
        auditoria.setFecha(LocalDate.of(2026,06,12));
    }

    @Test
    void testObtenerTodos(){
        when(auditoriaRepository.findAll()).thenReturn(List.of(auditoria));

        List<AuditoriaResponseDTO> auditorias = auditoriaService.obtenerTodos();

        assertNotNull(auditorias);
        assertEquals(1,auditorias.size());
    }

    @Test
    void testBuscarPorId(){
        when(auditoriaRepository.findById(1L)).thenReturn(Optional.of(auditoria));

        AuditoriaResponseDTO auditoria1 = auditoriaService.buscarPorId(1L);

        assertNotNull(auditoria1);
        assertEquals(auditoria.getIdAuditoria(), auditoria1.getIdAuditoria());
        assertEquals(auditoria.getDetalle(), auditoria1.getDetalle());
        assertEquals(auditoria.getFecha(), auditoria1.getFecha());

        verify(auditoriaRepository).findById(1L);
    }

    @Test
    void generarAuditoria(){
        AuditoriaRequestDTO dto = new AuditoriaRequestDTO();
        dto.setDetalle("Se creo un evento");
        dto.setFecha(LocalDate.of(2026,06,12));

        when(auditoriaRepository.save(any(Auditoria.class))).thenReturn(auditoria);

        AuditoriaResponseDTO auditoria1 = auditoriaService.generarAuditoria(dto);

        assertEquals(auditoria.getIdAuditoria(), auditoria1.getIdAuditoria());
        assertEquals(auditoria.getDetalle(), auditoria1.getDetalle());
        assertEquals(auditoria.getFecha(), auditoria1.getFecha());

        verify(auditoriaRepository).save(any(Auditoria.class));
    }


}
