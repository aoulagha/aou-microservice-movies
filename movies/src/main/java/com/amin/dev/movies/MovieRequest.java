package com.amin.dev.movies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public record MovieRequest(String originalName, String frenchName, Integer yearRelease) {
    public MovieRequest(String originalName, String frenchName, Integer yearRelease) {
        this.originalName = originalName;
        this.frenchName = frenchName;
        this.yearRelease = yearRelease;
    }
}
