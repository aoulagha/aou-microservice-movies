package com.amin.dev.clients.role;

public record DirectorRequest(Integer idMovie, Integer idDirector) {
    public DirectorRequest(Integer idMovie, Integer idDirector) {
        this.idMovie = idMovie;
        this.idDirector = idDirector;
    }
}
