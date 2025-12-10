package com.tinder.ud.usuario.controller;

import com.tinder.ud.usuario.dto.UsuarioDTO;
import com.tinder.ud.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints CRUD completos para perfiles de usuario.
 * 
 * @author Juan Sebastián Bravo Rojas
 * @version 1.0
 * @since 2025-12-09
 */
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param dto Datos del usuario a crear
     * @return ResponseEntity con el usuario creado
     * @throws RuntimeException si hay error en la creación
     */
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO dto) {
        try {
            return ResponseEntity.ok(usuarioService.crearUsuario(dto));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id ID del usuario
     * @return ResponseEntity con el usuario encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene un usuario por su nickname.
     * 
     * @param nickname Nickname del usuario
     * @return ResponseEntity con el usuario encontrado
     */
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<UsuarioDTO> obtenerPorNickname(@PathVariable String nickname) {
        try {
            return ResponseEntity.ok(usuarioService.obtenerUsuarioPorNickname(nickname));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene un usuario por su email.
     * 
     * @param email Email del usuario
     * @return ResponseEntity con el usuario encontrado
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> obtenerPorEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(usuarioService.obtenerUsuarioPorEmail(email));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param id  ID del usuario a actualizar
     * @param dto Nuevos datos del usuario
     * @return ResponseEntity con el usuario actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        try {
            return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param id ID del usuario a eliminar
     * @return ResponseEntity vacío con código 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos los usuarios del sistema.
     * 
     * @return ResponseEntity con lista de todos los usuarios
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    /**
     * Verifica si existe un usuario con el ID dado.
     * 
     * @param id ID del usuario a verificar
     * @return ResponseEntity con boolean indicando existencia
     */
    @GetMapping("/existe/{id}")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.existeUsuario(id));
    }
}
