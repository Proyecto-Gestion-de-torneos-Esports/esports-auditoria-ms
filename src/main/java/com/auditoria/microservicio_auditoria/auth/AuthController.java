package com.auditoria.microservicio_auditoria.auth;

import com.auditoria.microservicio_auditoria.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse inicioSesion(@RequestBody AuthRequest request){
        if(!request.getNombreUsuario().equals("admin") || !request.getClave().equals("1234")){
            throw new RuntimeException("Credenciales invalidas");
        }

        UserDetails usuario = new User(request.getNombreUsuario(),"", Collections.emptyList());

        String token = jwtService.generarToken(usuario);

        return new AuthResponse(token);
    }
}
