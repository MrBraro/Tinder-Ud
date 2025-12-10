package com.tinder.ud.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Punto de entrada para el microservicio de notificaciones.
 * Inicializa el contexto de Spring Boot y define los beans requeridos.
 *
 * @author Paula Martinez
 * @version 1.0
 * @since 2025-12-09
 */
@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
