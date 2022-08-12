package com.amin.dev.artists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    Optional<Artist> searchByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
