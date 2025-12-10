package com.tinder.ud.auth;

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
 * @author Juan Sebastian Bravo Rojas
 * @version 1.0
 * @since 2025-12-09
 */
@SpringBootApplication
public class AuthServiceApplication {

    /**
     * Método main que inicia el contexto de Spring Boot.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    /**
     * Crea y expone un bean de {@link RestTemplate}.
     *
     * @return instancia configurada de RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

