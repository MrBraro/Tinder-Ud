package com.tinder.ud.swipe.repository;

import com.tinder.ud.swipe.entity.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para gestionar operaciones CRUD de la entidad Swipe.
 * Proporciona consultas adicionales para verificar likes recíprocos.
 * 
 * @author Juan
 * @version 3.0
 * @since 2025-12-09
 */
@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Long> {

    /**
     * Busca si existe un swipe previo entre dos usuarios.
     *
     * @param idOrigen ID del usuario que realiza la acción.
     * @param idDestino ID del usuario que recibe el swipe.
     * @return Optional con el Swipe si existe.
     */
    Optional<Swipe> findByIdOrigenAndIdDestino(Long idOrigen, Long idDestino);

    /**
     * Verifica si existe un LIKE específico entre dos usuarios.
     *
     * @param idOrigen ID del origen.
     * @param idDestino ID del destino.
     * @param esLike Debe ser true para match.
     * @return true si existe el like, false si no.
     */
    @org.springframework.data.jpa.repository.Query(
        "SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
        "FROM Swipe s WHERE s.idOrigen = :idOrigen AND s.idDestino = :idDestino AND s.esLike = :esLike"
    )
    boolean existsByIdOrigenAndIdDestinoAndEsLike(
            @org.springframework.data.repository.query.Param("idOrigen") Long idOrigen,
            @org.springframework.data.repository.query.Param("idDestino") Long idDestino,
            @org.springframework.data.repository.query.Param("esLike") Boolean esLike
    );
}
