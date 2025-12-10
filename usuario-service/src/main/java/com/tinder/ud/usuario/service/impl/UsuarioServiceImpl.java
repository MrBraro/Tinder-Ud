package com.tinder.ud.usuario.service.impl;

import com.tinder.ud.usuario.dto.UsuarioDTO;
import com.tinder.ud.usuario.entity.Usuario;
import com.tinder.ud.usuario.repository.UsuarioRepository;
import com.tinder.ud.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de usuarios.
 * Contiene la lógica de negocio para la creación, consulta, actualización
 * y eliminación de usuarios.
 *
 * También incluye mapeo entre entidades y DTOs.
 * 
 * @author Juan Sebastian Bravo Rojas
 * @version 3.0
 * @since 2025-12-10
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param dto datos del usuario a crear
     * @return UsuarioDTO con los datos creados
     */
    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByNickname(dto.getNickname())) {
            throw new RuntimeException("El nickname ya está en uso");
        }
        Usuario usuario = mapToEntity(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        return mapToDTO(guardado);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id identificador del usuario
     * @return UsuarioDTO con la información encontrada
     */
    @Override
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapToDTO(usuario);
    }

    /**
     * Obtiene un usuario por su nickname.
     *
     * @param nickname nickname del usuario
     * @return UsuarioDTO con la información encontrada
     */
    @Override
    public UsuarioDTO obtenerUsuarioPorNickname(String nickname) {
        Usuario usuario = usuarioRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con nickname: " + nickname));
        return mapToDTO(usuario);
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email correo del usuario
     * @return UsuarioDTO con la información encontrada
     */
    @Override
    public UsuarioDTO obtenerUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        return mapToDTO(usuario);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id  ID del usuario a actualizar
     * @param dto datos nuevos para modificar
     * @return UsuarioDTO actualizado
     */
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

        Usuario actualizado = usuarioRepository.save(usuario);
        return mapToDTO(actualizado);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario
     */
    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Lista todos los usuarios registrados.
     *
     * @return lista de UsuarioDTO
     */
    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Verifica si un usuario existe por su ID.
     *
     * @param id ID del usuario
     * @return true si existe, false si no
     */
    @Override
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }

    /**
     * Convierte una entidad Usuario a DTO.
     * @param Usuario
     */
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

    /**
     * Convierte un DTO a entidad Usuario.
     * @param UsuarioDTO
     */
    private Usuario mapToEntity(UsuarioDTO dto) {
        Usuario entity = new Usuario();
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
