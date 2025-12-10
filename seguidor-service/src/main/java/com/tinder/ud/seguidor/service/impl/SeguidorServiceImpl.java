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

@Service
public class SeguidorServiceImpl implements SeguidorService {

    @Autowired
    private SeguidorRepository seguidorRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String USUARIO_SERVICE_URL = "http://localhost:8082/usuario/existe/";

    @Override
    public SeguidorDTO seguirUsuario(Long idSeguidor, Long idSeguido) {
        if (idSeguidor.equals(idSeguido)) {
            throw new RuntimeException("Un usuario no puede seguirse a sí mismo");
        }

        // Validar que ambos usuarios existan llamando a usuario-service
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

    @Override
    public void dejarDeSeguir(Long idSeguidor, Long idSeguido) {
        Seguidor seguidor = seguidorRepository.findByIdSeguidorAndIdSeguido(idSeguidor, idSeguido)
                .orElseThrow(() -> new RuntimeException("No se encontró la relación de seguimiento"));
        seguidorRepository.delete(seguidor);
    }

    @Override
    public boolean estaSiguiendo(Long idSeguidor, Long idSeguido) {
        return seguidorRepository.existsByIdSeguidorAndIdSeguido(idSeguidor, idSeguido);
    }

    @Override
    public List<SeguidorDTO> obtenerSeguidos(Long idSeguidor) {
        return seguidorRepository.findByIdSeguidor(idSeguidor).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeguidorDTO> obtenerSeguidores(Long idSeguido) {
        return seguidorRepository.findByIdSeguido(idSeguido).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private void verificarUsuarioExiste(Long usuarioId) {
        Boolean existe = restTemplate.getForObject(USUARIO_SERVICE_URL + usuarioId, Boolean.class);
        if (Boolean.FALSE.equals(existe)) {
            throw new RuntimeException("El usuario con ID " + usuarioId + " no existe");
        }
    }

    private SeguidorDTO mapToDTO(Seguidor entity) {
        SeguidorDTO dto = new SeguidorDTO();
        dto.setId(entity.getId());
        dto.setIdSeguidor(entity.getIdSeguidor());
        dto.setIdSeguido(entity.getIdSeguido());
        dto.setFecha(entity.getFecha());
        return dto;
    }
}
