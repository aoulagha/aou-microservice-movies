package com.amin.dev.clients.role;

public record ActorRequest(Integer idMovie, Integer idActor, String roleName) {
    public ActorRequest(Integer idMovie, Integer idActor, String roleName) {
        this.idMovie = idMovie;
        this.idActor = idActor;
        this.roleName = roleName;
    }
}
