package com.tinder.ud.swipe.service;

import com.tinder.ud.swipe.dto.SwipeDTO;

/**
 * Servicio que define operaciones relacionadas con swipes: registrar y validar likes.
 * 
 * @author Juan
 * @version 1.0
 * @since 2025-12-09
 */
public interface SwipeService {

    /**
     * Registra un swipe entre dos usuarios.
     *
     * @param swipeDTO Datos del swipe a registrar.
     * @return SwipeDTO con información guardada y posible match.
     */
    SwipeDTO registrarSwipe(SwipeDTO swipeDTO);

    /**
     * Verifica si existe un like entre dos usuarios.
     *
     * @param idOrigen Usuario que da el like.
     * @param idDestino Usuario que recibe el like.
     * @return true si existe like, de lo contrario false.
     */
    boolean existeLike(Long idOrigen, Long idDestino);
}
