package com.tinder.ud.match;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Punto de entrada principal del servicio de autenticación.
 * Inicializa la aplicación Spring Boot y expone beans necesarios.
 *
 * <p>Proporciona un {@link RestTemplate} para realizar llamadas HTTP
 * a otros servicios dentro del ecosistema.</p>
 *
 * @author Juan Estevan Ariza Ortiz
 * @version 1.0
 * @since 2025-12-09
 */
@SpringBootApplication
public class MatchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
