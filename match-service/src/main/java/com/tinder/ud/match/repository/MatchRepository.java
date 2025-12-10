package com.tinder.ud.match.repository;

import com.tinder.ud.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA encargado de gestionar operaciones CRUD
 * sobre la entidad {@link Match}.
 *
 * <p>Incluye métodos personalizados para búsqueda específica.</p>
 *
 * @version 3.0
 * @since 2025-12-09
 * @author Juan Estevan Ariza Ortiz
 */
@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    /**
     * Verifica si existe un match entre dos usuarios.
     *
     * @param idUsuario1 primer usuario
     * @param idUsuario2 segundo usuario
     * @return true si existe, false si no
     */
    boolean existsByIdUsuario1AndIdUsuario2(Long idUsuario1, Long idUsuario2);

    /**
     * Obtiene todos los matches donde el usuario aparece
     * como idUsuario1 o idUsuario2.
     *
     * @param idUsuario1 usuario buscado
     * @param idUsuario2 usuario buscado
     * @return lista de matches
     */
    List<Match> findByIdUsuario1OrIdUsuario2(Long idUsuario1, Long idUsuario2);
}
