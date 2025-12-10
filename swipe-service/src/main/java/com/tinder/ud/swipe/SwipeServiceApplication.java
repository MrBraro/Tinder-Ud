package com.tinder.ud.swipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Punto de entrada del microservicio de Swipe.
 * Configura el contexto de Spring Boot y expone el bean RestTemplate.
 *
 * @author Juan Estevan Ariza Ortiz
 * @version 1.0
 * @since 2025-12-09
 */
@SpringBootApplication
public class SwipeServiceApplication {

    /**
     * Método principal para ejecutar el microservicio.
     *
     * @param args Argumentos de consola.
     */
    public static void main(String[] args) {
        SpringApplication.run(SwipeServiceApplication.class, args);
    }

    /**
     * Crea una instancia de RestTemplate para consumo de servicios REST externos.
     *
     * @return RestTemplate configurado.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

