package com.auditoria.microservicio_auditoria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(AuditoriaNotFoundException.class)
    public ResponseEntity<?> manejoAuditoriaNoEncontrada(AuditoriaNotFoundException e){
        HashMap<String, Object> error = new HashMap<>();
        error.put("Estado",404);
        error.put("Mensaje",e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejoValidaciones(MethodArgumentNotValidException e) {
        HashMap<String, Object> error = new HashMap<>();
        error.put("Estado",400);
        e.getBindingResult().getFieldErrors().forEach(errores -> error.put(errores.getField(), errores.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejoGeneral(Exception e){
        HashMap<String, Object> error = new HashMap<>();
        error.put("Estado", 500);
        //error.put("Mensaje", "Error interno en el servidor");
        error.put("Mensaje", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }



}
