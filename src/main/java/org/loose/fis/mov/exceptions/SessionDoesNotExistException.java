package org.loose.fis.mov.exceptions;

public class SessionDoesNotExistException extends Exception {
    public SessionDoesNotExistException() {
        super("There is no logged in user!");
    }
}
