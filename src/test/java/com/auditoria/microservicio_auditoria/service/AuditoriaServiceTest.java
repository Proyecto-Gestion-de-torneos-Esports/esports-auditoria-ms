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
    private AuditoriaRepository auditoriaRepository;

    @InjectMocks
    private AuditoriaService auditoriaService;

    private Auditoria auditoria;

    @BeforeEach
    void setUp() {
        auditoria = new Auditoria();
        auditoria.setIdAuditoria(1L);
        auditoria.setDetalle("Se creo un evento");
        auditoria.setFecha(LocalDate.of(2026, 6, 12));
    }

    @Test
    void testObtenerTodos() {
        when(auditoriaRepository.findAll()).thenReturn(List.of(auditoria));

        List<AuditoriaResponseDTO> auditorias = auditoriaService.obtenerTodos();

        assertNotNull(auditorias);
        assertEquals(1, auditorias.size());

        assertEquals(1L, auditorias.get(0).getIdAuditoria());
        assertEquals("Se creo un evento", auditorias.get(0).getDetalle());
        assertEquals(LocalDate.of(2026, 6, 12), auditorias.get(0).getFecha());

        verify(auditoriaRepository).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(auditoriaRepository.findById(1L)).thenReturn(Optional.of(auditoria));

        AuditoriaResponseDTO auditoriaEncontrada = auditoriaService.buscarPorId(1L);

        assertNotNull(auditoriaEncontrada);
        assertEquals(auditoria.getIdAuditoria(), auditoriaEncontrada.getIdAuditoria());
        assertEquals(auditoria.getDetalle(), auditoriaEncontrada.getDetalle());
        assertEquals(auditoria.getFecha(), auditoriaEncontrada.getFecha());

        verify(auditoriaRepository).findById(1L);
    }

    @Test
    void generarAuditoria() {
        AuditoriaRequestDTO dto = new AuditoriaRequestDTO();
        dto.setDetalle("Se creo un evento");
        dto.setFecha(LocalDate.of(2026, 6, 12));

        when(auditoriaRepository.save(any(Auditoria.class))).thenReturn(auditoria);

        AuditoriaResponseDTO auditoriaGenerada = auditoriaService.generarAuditoria(dto);

        assertNotNull(auditoriaGenerada);
        assertEquals(auditoria.getIdAuditoria(), auditoriaGenerada.getIdAuditoria());
        assertEquals(auditoria.getDetalle(), auditoriaGenerada.getDetalle());
        assertEquals(auditoria.getFecha(), auditoriaGenerada.getFecha());

        verify(auditoriaRepository).save(any(Auditoria.class));
    }
}