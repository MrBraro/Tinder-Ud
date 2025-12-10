package com.tinder.ud.usuario.repository;

import com.tinder.ud.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNickname(String nickname);

    Optional<Usuario> findByEmail(String email);

    boolean existsByNickname(String nickname);
}
