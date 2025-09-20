package com.xyz.threatre.aggregator.exceptions;

import lombok.Getter;

@Getter
public class TheatreIntegrationException extends RuntimeException {
    private String theatreName;

    public TheatreIntegrationException(String theatreName,String message, Throwable cause){
        super(message,cause);
        this.theatreName = theatreName;
    }

}