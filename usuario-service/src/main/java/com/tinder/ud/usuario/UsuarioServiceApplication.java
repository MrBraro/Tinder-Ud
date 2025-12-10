package com.tinder.ud.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Aplicación principal del microservicio de Usuario.
 * Configura el contexto de Spring Boot y expone beans compartidos.
 *
 * @author Juan Sebastián Bravo Rojas
 * @version 1.0
 * @since 2025-12-10
 */
@SpringBootApplication
public class UsuarioServiceApplication {

    /**
     * Método principal que inicia el microservicio.
     *
     * @param args argumentos de ejecución
     */
    public static void main(String[] args) {
        SpringApplication.run(UsuarioServiceApplication.class, args);
    }

    /**
     * Crea un bean RestTemplate para realizar llamadas HTTP a otros servicios.
     *
     * @return una instancia de RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
