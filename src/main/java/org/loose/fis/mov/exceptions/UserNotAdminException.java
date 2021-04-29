package org.loose.fis.mov.exceptions;

public class UserNotAdminException extends Exception {
    public UserNotAdminException() {
        super("This user is not an admin!");
    }
}
