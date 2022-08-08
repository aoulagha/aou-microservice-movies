package com.amin.dev.movies.error;

public class MovieValidationException extends MovieException{

    public MovieValidationException(String message) {
        super("Validation Issue : " + message);
    }
}
