package com.amin.dev.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectingRepository extends JpaRepository<Directing, Integer> {

    Optional<Directing> findByIdMovieAndIdDirector(Integer idMovie, Integer idActor);
}
