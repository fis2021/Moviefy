package org.loose.fis.mov.exceptions;

public class UserNotRegisteredException extends Exception {
    private String username;

    public UserNotRegisteredException(String username) {
        super(String.format("A user with the name %s does not exist!", username));
        this.username = username;
    }
}
