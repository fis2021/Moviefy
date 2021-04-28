package org.loose.fis.mov.exceptions;

public class PasswordIncorrectException extends Exception {
    public PasswordIncorrectException() {
        super("Incorrect password!");
    }
}
