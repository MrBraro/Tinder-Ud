package com.tinder.ud.seguidor.repository;

import com.tinder.ud.seguidor.entity.Seguidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para manejar operaciones CRUD de la entidad Seguidor.
 * Permite validar y consultar relaciones de seguimiento.
 *
 * @author Juan Estevan Ariza Ortiz
 * @version 1.0
 * @since 2025-12-09
 */
@Repository
public interface SeguidorRepository extends JpaRepository<Seguidor, Long> {

    /**
     * Verifica si un seguidor ya sigue a un usuario.
     *
     * @param idSeguidor ID del seguidor
     * @param idSeguido ID del seguido
     * @return true si existe la relación
     */
    boolean existsByIdSeguidorAndIdSeguido(Long idSeguidor, Long idSeguido);

    /**
     * Obtiene una relación específica entre seguidor y seguido.
     *
     * @param idSeguidor ID del seguidor
     * @param idSeguido ID del seguido
     * @return relación opcional
     */
    Optional<Seguidor> findByIdSeguidorAndIdSeguido(Long idSeguidor, Long idSeguido);

    /**
     * Lista a quién sigue un usuario.
     *
     * @param idSeguidor ID del seguidor
     * @return lista de relaciones
     */
    List<Seguidor> findByIdSeguidor(Long idSeguidor);

    /**
     * Lista quién sigue a un usuario.
     *
     * @param idSeguido ID del usuario seguido
     * @return lista de relaciones
     */
    List<Seguidor> findByIdSeguido(Long idSeguido);
}
