package com.tinder.ud.usuario.service;

import com.tinder.ud.usuario.dto.UsuarioDTO;
import java.util.List;

/**
 * Interfaz que define las operaciones del servicio de usuario.
 * Separa la lógica de negocio de los controladores y el repositorio.
 *
 * Incluye operaciones CRUD y búsquedas por distintos campos.
 */
public interface UsuarioService {

    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO obtenerUsuarioPorId(Long id);

    UsuarioDTO obtenerUsuarioPorNickname(String nickname);

    UsuarioDTO obtenerUsuarioPorEmail(String email);

    UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO);

    void eliminarUsuario(Long id);

    List<UsuarioDTO> listarUsuarios();

    boolean existeUsuario(Long id);
}

