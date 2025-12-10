package com.tinder.ud.media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Punto de entrada para el servicio de Media.
 * Configura el contexto de Spring Boot y expone el bean RestTemplate
 * utilizado para realizar solicitudes HTTP a otros microservicios.
 * 
 * Métodos:
 * - main(String[] args): inicia la aplicación.
 * - restTemplate(): retorna un RestTemplate configurable para inyecciones.
 * 
 * @author Paula Martinez
 * @version 1.0
 * @since 2025-12-10
 */
@SpringBootApplication
public class MediaServiceApplication {

    /**
     * Inicia el microservicio de media.
     * 
     * @param args argumentos del sistema.
     */
    public static void main(String[] args) {
        SpringApplication.run(MediaServiceApplication.class, args);
    }

    /**
     * Crea un bean RestTemplate para realizar llamadas REST.
     * 
     * @return instancia de RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
