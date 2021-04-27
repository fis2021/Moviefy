package org.loose.fis.mov.exceptions;

public class EmailFormatInvalidException extends Exception {
    public EmailFormatInvalidException() {
        super("The e-mail address format is invalid!");
    }
}
