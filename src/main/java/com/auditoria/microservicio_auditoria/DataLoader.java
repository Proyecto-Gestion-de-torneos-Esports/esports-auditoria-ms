package com.auditoria.microservicio_auditoria;

import com.auditoria.microservicio_auditoria.model.Auditoria;
import com.auditoria.microservicio_auditoria.repository.AuditoriaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Profile("dev")
@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();

        for(int i = 0; i<3; i++){
            Auditoria auditoria = new Auditoria();
            auditoria.setIdAuditoria((long)(i+1));
            auditoria.setDetalle("Se creo un evento");
            auditoria.setFecha(faker.timeAndDate().past(365, TimeUnit.DAYS).atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            auditoriaRepository.save(auditoria);
        }
    }
}
