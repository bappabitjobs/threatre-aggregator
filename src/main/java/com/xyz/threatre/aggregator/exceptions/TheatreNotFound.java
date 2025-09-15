package com.xyz.threatre.aggregator.exceptions;

import java.io.Serial;

public class TheatreNotFound extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2995350098352987873L;

    public TheatreNotFound() {
        super("Theater does not Exists");
    }
}