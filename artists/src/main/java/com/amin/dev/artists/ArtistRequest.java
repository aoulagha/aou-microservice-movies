package com.amin.dev.artists;

import java.time.LocalDate;

public record ArtistRequest(String firstName, String lastName
        , LocalDate birthDate) {
    public ArtistRequest(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
