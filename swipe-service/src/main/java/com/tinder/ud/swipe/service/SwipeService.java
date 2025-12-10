package com.tinder.ud.swipe.service;

import com.tinder.ud.swipe.dto.SwipeDTO;

public interface SwipeService {
    SwipeDTO registrarSwipe(SwipeDTO swipeDTO);

    boolean existeLike(Long idOrigen, Long idDestino);
}
