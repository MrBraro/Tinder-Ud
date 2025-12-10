package com.tinder.ud.usuario.service;

import com.tinder.ud.usuario.dto.UsuarioDTO;
import java.util.List;

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
