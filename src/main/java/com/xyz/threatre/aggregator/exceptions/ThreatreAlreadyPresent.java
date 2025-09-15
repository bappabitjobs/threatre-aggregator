package com.xyz.threatre.aggregator.exceptions;

public class ThreatreAlreadyPresent extends RuntimeException{
    private static final long serialVersionUID = 6386810783666583528L;

    public ThreatreAlreadyPresent() {
        super("Theater is already Present on this Address");
    }
}