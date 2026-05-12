package com.auditoria.microservicio_auditoria.service;

import com.auditoria.microservicio_auditoria.dto.AuditoriaRequestDTO;
import com.auditoria.microservicio_auditoria.dto.AuditoriaResponseDTO;
import com.auditoria.microservicio_auditoria.exception.AuditoriaNotFoundException;
import com.auditoria.microservicio_auditoria.model.Auditoria;
import com.auditoria.microservicio_auditoria.repository.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaResponseDTO mapToDTO(Auditoria auditoria){
        return new AuditoriaResponseDTO(
                auditoria.getIdAuditoria(),
                auditoria.getDetalle(),
                auditoria.getFecha()
        );
    }

    public List<AuditoriaResponseDTO> obtenerTodos(){
        return auditoriaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AuditoriaResponseDTO buscarPorId(Long id){
         Optional<Auditoria> auditoria = auditoriaRepository.findById(id);

         if(auditoria.isPresent()){
             return auditoria.map(this::mapToDTO).orElseThrow();
         }
         throw new AuditoriaNotFoundException("Auditoria con id "+id+" no encontrada");
    }

    public AuditoriaResponseDTO generarAuditoria(AuditoriaRequestDTO auditoria){
        Auditoria auditoria1 = new Auditoria(null, auditoria.getDetalle(), auditoria.getFecha());
        return mapToDTO(auditoriaRepository.save(auditoria1));
    }

}
