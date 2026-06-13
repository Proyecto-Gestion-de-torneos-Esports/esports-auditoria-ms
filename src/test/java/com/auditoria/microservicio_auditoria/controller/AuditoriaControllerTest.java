package com.auditoria.microservicio_auditoria.controller;

import com.auditoria.microservicio_auditoria.dto.AuditoriaRequestDTO;
import com.auditoria.microservicio_auditoria.dto.AuditoriaResponseDTO;
import com.auditoria.microservicio_auditoria.service.AuditoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuditoriaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuditoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuditoriaService auditoriaService;

    private AuditoriaResponseDTO auditoria;

    @BeforeEach
    void setUp(){
        auditoria = new AuditoriaResponseDTO();
        auditoria.setIdAuditoria(1L);
        auditoria.setDetalle("Se creo un evento");
        auditoria.setFecha(LocalDate.of(2026,06,12));
    }

    @Test
    void testBuscarTodos() throws Exception{
        when(auditoriaService.obtenerTodos()).thenReturn(List.of(auditoria));

        mockMvc.perform(get("/api/auditoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAuditoria").value(1L))
                .andExpect(jsonPath("$[0].detalle").value("Se creo un evento"))
                .andExpect(jsonPath("$[0].fecha").value("2026-06-12"));

        List<AuditoriaResponseDTO> auditorias = auditoriaService.obtenerTodos();

        assertNotNull(auditorias);
        assertEquals(1, auditorias.size());
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(auditoriaService.buscarPorId(1L)).thenReturn(auditoria);

        mockMvc.perform(get("/api/auditoria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAuditoria").value(1L))
                .andExpect(jsonPath("$.detalle").value("Se creo un evento"))
                .andExpect(jsonPath("$.fecha").value("2026-06-12"));

        verify(auditoriaService).buscarPorId(1L);
    }

    @Test
    void testAgregarAuditoria() throws Exception{
        when(auditoriaService.generarAuditoria(any(AuditoriaRequestDTO.class))).thenReturn(auditoria);

        mockMvc.perform(post("/api/auditoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "detalle":"Se creo un evento",
                            "fecha":"2026-06-12"
                        }
                        """))
                .andExpect(status().isCreated());

        verify(auditoriaService).generarAuditoria(any(AuditoriaRequestDTO.class));
    }
}
