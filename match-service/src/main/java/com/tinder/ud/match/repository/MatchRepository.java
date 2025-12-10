package com.tinder.ud.match.repository;

import com.tinder.ud.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    // Check if match exists (order sensitive or not? Usually we store sorted or
    // check both)
    boolean existsByIdUsuario1AndIdUsuario2(Long idUsuario1, Long idUsuario2);

    // Simple way: check both permutations? Or enforce id1 < id2 rule.
    // We will do check on service layer.

    List<Match> findByIdUsuario1OrIdUsuario2(Long idUsuario1, Long idUsuario2);
}
