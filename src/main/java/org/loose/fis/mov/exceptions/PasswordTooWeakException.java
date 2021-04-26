package org.loose.fis.mov.exceptions;

public class PasswordTooWeakException extends Exception{
    public PasswordTooWeakException() {
        super("The password must be at least 8 characters long!");
    }
}
