package com.tinder.ud.swipe.repository;

import com.tinder.ud.swipe.entity.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Long> {
    Optional<Swipe> findByIdOrigenAndIdDestino(Long idOrigen, Long idDestino);

    @org.springframework.data.jpa.repository.Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Swipe s WHERE s.idOrigen = :idOrigen AND s.idDestino = :idDestino AND s.esLike = :esLike")
    boolean existsByIdOrigenAndIdDestinoAndEsLike(
            @org.springframework.data.repository.query.Param("idOrigen") Long idOrigen,
            @org.springframework.data.repository.query.Param("idDestino") Long idDestino,
            @org.springframework.data.repository.query.Param("esLike") Boolean esLike);
}
