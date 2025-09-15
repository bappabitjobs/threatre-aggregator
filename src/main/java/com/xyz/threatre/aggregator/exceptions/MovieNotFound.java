package com.xyz.threatre.aggregator.exceptions;

import java.io.Serial;

public class MovieNotFound extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5385129013790060351L;

    public MovieNotFound() {
        super("Movie dose not found");
    }
}