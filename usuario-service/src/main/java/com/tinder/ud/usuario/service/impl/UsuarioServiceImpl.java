package com.tinder.ud.usuario.service.impl;

import com.tinder.ud.usuario.dto.UsuarioDTO;
import com.tinder.ud.usuario.entity.Usuario;
import com.tinder.ud.usuario.repository.UsuarioRepository;
import com.tinder.ud.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByNickname(dto.getNickname())) {
            throw new RuntimeException("El nickname ya está en uso");
        }
        Usuario usuario = mapToEntity(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        return mapToDTO(guardado);
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapToDTO(usuario);
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorNickname(String nickname) {
        Usuario usuario = usuarioRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con nickname: " + nickname));
        return mapToDTO(usuario);
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        return mapToDTO(usuario);
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEdad(dto.getEdad());
        usuario.setGenero(dto.getGenero());
        usuario.setCiudad(dto.getCiudad());
        usuario.setDescripcion(dto.getDescripcion());
        // No actualizamos nickname ni email por seguridad básica aquí

        Usuario actualizado = usuarioRepository.save(usuario);
        return mapToDTO(actualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }

    // Mappers sencillos (podría usar MapStruct pero lo haré manual para evitar
    // config extra)
    private UsuarioDTO mapToDTO(Usuario entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setNickname(entity.getNickname());
        dto.setEmail(entity.getEmail());
        dto.setEdad(entity.getEdad());
        dto.setGenero(entity.getGenero());
        dto.setCiudad(entity.getCiudad());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFotoUrl(entity.getFotoUrl());
        return dto;
    }

    private Usuario mapToEntity(UsuarioDTO dto) {
        Usuario entity = new Usuario();
        // ID se genera auto
        entity.setNombre(dto.getNombre());
        entity.setApellidos(dto.getApellidos());
        entity.setNickname(dto.getNickname());
        entity.setEmail(dto.getEmail());
        entity.setEdad(dto.getEdad());
        entity.setGenero(dto.getGenero());
        entity.setCiudad(dto.getCiudad());
        entity.setDescripcion(dto.getDescripcion());
        entity.setFotoUrl(dto.getFotoUrl());
        return entity;
    }
}
