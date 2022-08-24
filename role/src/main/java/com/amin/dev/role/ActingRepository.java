package com.amin.dev.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActingRepository extends JpaRepository<Acting, Integer> {
    Optional<Acting> findByIdMovieAndIdActor(Integer idMovie, Integer idActor);
}
