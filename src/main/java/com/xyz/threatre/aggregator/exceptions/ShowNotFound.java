package com.xyz.threatre.aggregator.exceptions;

import java.io.Serial;

public class ShowNotFound extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -1242894843912292020L;

    public ShowNotFound() {
        super("No show found");
    }
}
