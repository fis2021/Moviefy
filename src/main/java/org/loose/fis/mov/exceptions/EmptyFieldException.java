package org.loose.fis.mov.exceptions;

public class EmptyFieldException extends Exception {
    public EmptyFieldException() {
        super("A required field is empty!");
    }
}
