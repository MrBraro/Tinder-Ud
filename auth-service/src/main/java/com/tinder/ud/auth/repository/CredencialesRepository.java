package com.tinder.ud.auth.repository;

import com.tinder.ud.auth.entity.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {
    Optional<Credenciales> findByEmail(String email);

    boolean existsByEmail(String email);
}
