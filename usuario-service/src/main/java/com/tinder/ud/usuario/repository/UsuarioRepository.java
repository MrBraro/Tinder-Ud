package com.tinder.ud.usuario.repository;

import com.tinder.ud.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para gestionar operaciones CRUD sobre la entidad Usuario.
 * Provee métodos personalizados para búsqueda por nickname y email.
 *
 * @author 
 * @version 1.0
 * @since 2025-12-10
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nickname.
     *
     * @param nickname nickname del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByNickname(String nickname);

    /**
     * Busca un usuario por su email.
     *
     * @param email email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si ya existe un usuario con un nickname.
     *
     * @param nickname nickname a consultar
     * @return true si existe, false si no
     */
    boolean existsByNickname(String nickname);
}
