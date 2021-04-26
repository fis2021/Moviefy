package org.loose.fis.mov.exceptions;

public class CinemaCapacityNotUnsignedIntegerException extends Exception {
    public CinemaCapacityNotUnsignedIntegerException() {
        super("The inputted capacity is not a valid capacity!");
    }
}
