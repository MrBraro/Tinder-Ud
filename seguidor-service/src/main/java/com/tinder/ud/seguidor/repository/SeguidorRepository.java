package com.tinder.ud.seguidor.repository;

import com.tinder.ud.seguidor.entity.Seguidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeguidorRepository extends JpaRepository<Seguidor, Long> {
    boolean existsByIdSeguidorAndIdSeguido(Long idSeguidor, Long idSeguido);

    Optional<Seguidor> findByIdSeguidorAndIdSeguido(Long idSeguidor, Long idSeguido);

    List<Seguidor> findByIdSeguidor(Long idSeguidor); // A quién sigo

    List<Seguidor> findByIdSeguido(Long idSeguido); // Quién me sigue
}
