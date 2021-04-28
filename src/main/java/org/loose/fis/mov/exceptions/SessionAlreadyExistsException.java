package org.loose.fis.mov.exceptions;

public class SessionAlreadyExistsException extends Exception {
    public SessionAlreadyExistsException() {
        super("A user is already logged in!");
    }
}
