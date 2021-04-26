package org.loose.fis.mov.exceptions;

public class EmailAddressAlreadyUsedException extends Exception{
    private final String email;

    public EmailAddressAlreadyUsedException(String email) {
        super(String.format("The e-mail address %s is already used!", email));
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
