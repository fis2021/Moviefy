package org.loose.fis.mov.exceptions;

public class CinemaAlreadyExistsException extends Exception {
    private String name;

    public CinemaAlreadyExistsException(String name) {
        super(String.format("A cinema with the name %s already exists!", name));
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
