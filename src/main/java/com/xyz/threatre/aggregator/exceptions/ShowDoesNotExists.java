package com.xyz.threatre.aggregator.exceptions;

public class ShowDoesNotExists extends RuntimeException {

    public ShowDoesNotExists() {
        super("Show does not exists");
    }
}