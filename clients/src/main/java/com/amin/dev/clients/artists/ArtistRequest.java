package com.amin.dev.clients.artists;

import java.time.LocalDate;

public record ArtistRequest(String firstName, String lastName
        , LocalDate birthDate, String role) {
    public ArtistRequest(String firstName, String lastName, LocalDate birthDate, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.role = role;
    }
}
