package com.tinder.ud.auth.repository;

import com.tinder.ud.auth.entity.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repositorio JPA para gestionar entidades {@link Credenciales}.
 * Proporciona métodos para búsquedas por email y validación de existencia.
 * 
 * @author Juan Sebastian BRavo Rojas
 * @version 3.0
 * @since 2025-12-09
 */
@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {
    Optional<Credenciales> findByEmail(String email);

    boolean existsByEmail(String email);
}
