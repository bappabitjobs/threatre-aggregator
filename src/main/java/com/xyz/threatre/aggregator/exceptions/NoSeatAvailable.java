package com.xyz.threatre.aggregator.exceptions;

import java.io.Serial;

public class NoSeatAvailable extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2645769068473071481L;

    public NoSeatAvailable() {
        super("Requested Seats Are Not Available");
    }
}
