package org.loose.fis.mov.exceptions;

public class UserNotRegisteredException extends Exception {
    public UserNotRegisteredException() {
        super("This user does not exist!");
    }
}