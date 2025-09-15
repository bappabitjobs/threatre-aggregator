package com.xyz.threatre.aggregator.exceptions;

public class NoThreatreFound extends RuntimeException{
    private static final long serialVersionUID = -6422808899117002534L;

    public NoThreatreFound() {
        super("No threatre found in this address");
    }
}
