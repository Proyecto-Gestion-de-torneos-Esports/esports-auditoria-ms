package com.auditoria.microservicio_auditoria.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.auditoria.microservicio_auditoria.assembler.AuditoriaModelAssembler;
import com.auditoria.microservicio_auditoria.dto.AuditoriaRequestDTO;
import com.auditoria.microservicio_auditoria.dto.AuditoriaResponseDTO;
import com.auditoria.microservicio_auditoria.service.AuditoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(AuditoriaController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(roles = "ADMIN")
@Import(AuditoriaModelAssembler.class)
public class AuditoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuditoriaService auditoriaService;

    private AuditoriaResponseDTO auditoria;
    private AuditoriaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        auditoria = new AuditoriaResponseDTO();
        auditoria.setIdAuditoria(1L);
        auditoria.setDetalle("Se creo un evento");
        auditoria.setFecha(LocalDate.of(2026, 6, 12));

        requestDTO = new AuditoriaRequestDTO();
        requestDTO.setDetalle("Se creo un evento");
        requestDTO.setFecha(LocalDate.of(2026, 6, 12));
    }

    @Test
    void testBuscarTodos() throws Exception {
        when(auditoriaService.obtenerTodos()).thenReturn(List.of(auditoria));

        mockMvc.perform(get("/api/auditoria")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(auditoriaService).obtenerTodos();
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(auditoriaService.buscarPorId(1L)).thenReturn(auditoria);

        mockMvc.perform(get("/api/auditoria/1")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAuditoria").value(1L))
                .andExpect(jsonPath("$.detalle").value("Se creo un evento"))
                .andExpect(jsonPath("$.fecha").value("2026-06-12"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(auditoriaService).buscarPorId(1L);
    }

    @Test
    void testAgregarAuditoria() throws Exception {
        when(auditoriaService.generarAuditoria(any(AuditoriaRequestDTO.class))).thenReturn(auditoria);

        mockMvc.perform(post("/api/auditoria")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAuditoria").value(1L))
                .andExpect(jsonPath("$.detalle").value("Se creo un evento"))
                .andExpect(jsonPath("$.fecha").value("2026-06-12"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(auditoriaService).generarAuditoria(any(AuditoriaRequestDTO.class));
    }
}