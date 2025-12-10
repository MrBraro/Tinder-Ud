package com.tinder.ud.seguidor.service.impl;

import com.tinder.ud.seguidor.dto.SeguidorDTO;
import com.tinder.ud.seguidor.entity.Seguidor;
import com.tinder.ud.seguidor.repository.SeguidorRepository;
import com.tinder.ud.seguidor.service.SeguidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio encargado de gestionar las relaciones de seguimiento entre usuarios.
 * Valida existencia de usuarios, crea relaciones, las elimina y consulta seguidores y seguidos.
 *
 * @author Juan Estevan Ariza Ortiz
 * @version 3.0
 * @since 2025-12-09
 */
@Service
public class SeguidorServiceImpl implements SeguidorService {

    @Autowired
    private SeguidorRepository seguidorRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String USUARIO_SERVICE_URL = "http://localhost:8082/usuario/existe/";

    /**
     * Crea una relación de seguimiento entre dos usuarios.
     *
     * @param idSeguidor ID del usuario que seguirá
     * @param idSeguido ID del usuario que será seguido
     * @return SeguidorDTO con la relación creada
     * @throws RuntimeException si el usuario se intenta seguir a sí mismo,
     *                          si no existen los usuarios o si la relación ya existe
     */
    @Override
    public SeguidorDTO seguirUsuario(Long idSeguidor, Long idSeguido) {
        if (idSeguidor.equals(idSeguido)) {
            throw new RuntimeException("Un usuario no puede seguirse a sí mismo");
        }

        // Validar existencia de usuarios
        verificarUsuarioExiste(idSeguidor);
        verificarUsuarioExiste(idSeguido);

        if (seguidorRepository.existsByIdSeguidorAndIdSeguido(idSeguidor, idSeguido)) {
            throw new RuntimeException("El usuario ya sigue a este usuario");
        }

        Seguidor seguidor = new Seguidor();
        seguidor.setIdSeguidor(idSeguidor);
        seguidor.setIdSeguido(idSeguido);
        seguidor.setFecha(LocalDateTime.now());

        return mapToDTO(seguidorRepository.save(seguidor));
    }

    /**
     * Elimina una relación de seguimiento entre dos usuarios.
     *
     * @param idSeguidor ID del usuario que dejará de seguir
     * @param idSeguido ID del usuario que será dejado de seguir
     * @throws RuntimeException si la relación no existe
     */
    @Override
    public void dejarDeSeguir(Long idSeguidor, Long idSeguido) {
        Seguidor seguidor = seguidorRepository.findByIdSeguidorAndIdSeguido(idSeguidor, idSeguido)
                .orElseThrow(() -> new RuntimeException("No se encontró la relación de seguimiento"));
        seguidorRepository.delete(seguidor);
    }

    /**
     * Verifica si un usuario está siguiendo a otro.
     *
     * @param idSeguidor ID del usuario que podría estar siguiendo
     * @param idSeguido ID del usuario que podría ser seguido
     * @return true si el seguidor sigue al seguido; false de lo contrario
     */
    @Override
    public boolean estaSiguiendo(Long idSeguidor, Long idSeguido) {
        return seguidorRepository.existsByIdSeguidorAndIdSeguido(idSeguidor, idSeguido);
    }

    /**
     * Obtiene la lista de usuarios que un usuario sigue.
     *
     * @param idSeguidor ID del usuario
     * @return lista de SeguidorDTO representando a quién sigue
     */
    @Override
    public List<SeguidorDTO> obtenerSeguidos(Long idSeguidor) {
        return seguidorRepository.findByIdSeguidor(idSeguidor).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de usuarios que siguen a un usuario.
     *
     * @param idSeguido ID del usuario
     * @return lista de SeguidorDTO representando sus seguidores
     */
    @Override
    public List<SeguidorDTO> obtenerSeguidores(Long idSeguido) {
        return seguidorRepository.findByIdSeguido(idSeguido).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Verifica si un usuario existe consultando el microservicio usuario.
     *
     * @param usuarioId ID del usuario a validar
     * @throws RuntimeException si el usuario no existe
     */
    private void verificarUsuarioExiste(Long usuarioId) {
        Boolean existe = restTemplate.getForObject(USUARIO_SERVICE_URL + usuarioId, Boolean.class);
        if (Boolean.FALSE.equals(existe)) {
            throw new RuntimeException("El usuario con ID " + usuarioId + " no existe");
        }
    }

    /**
     * Convierte una entidad Seguidor en un DTO SeguidorDTO.
     *
     * @param entity entidad Seguidor
     * @return DTO con los datos de la entidad
     */
    private SeguidorDTO mapToDTO(Seguidor entity) {
        SeguidorDTO dto = new SeguidorDTO();
        dto.setId(entity.getId());
        dto.setIdSeguidor(entity.getIdSeguidor());
        dto.setIdSeguido(entity.getIdSeguido());
        dto.setFecha(entity.getFecha());
        return dto;
    }
}
