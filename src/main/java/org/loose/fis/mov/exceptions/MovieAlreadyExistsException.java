package org.loose.fis.mov.exceptions;

public class MovieAlreadyExistsException extends Exception {
    public MovieAlreadyExistsException() {
        super("This movie already exists!");
    }
}
