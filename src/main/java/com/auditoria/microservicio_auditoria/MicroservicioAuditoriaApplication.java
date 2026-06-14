package com.auditoria.microservicio_auditoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class MicroservicioAuditoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioAuditoriaApplication.class, args);
	}

}
