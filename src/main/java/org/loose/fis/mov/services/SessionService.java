package org.loose.fis.mov.services;

import org.loose.fis.mov.exceptions.SessionAlreadyExistsException;
import org.loose.fis.mov.exceptions.SessionDoesNotExistException;
import org.loose.fis.mov.model.User;

public class SessionService {
    private static User loggedInUser;

    public static void startSession(User user) throws SessionAlreadyExistsException {
        if (checkSessionExists()) {
            throw new SessionAlreadyExistsException();
        }
        loggedInUser = user;
    }

    public static void destroySession() throws SessionDoesNotExistException {
        if (!checkSessionExists()) {
            throw new SessionDoesNotExistException();
        }
        loggedInUser = null;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    private static boolean checkSessionExists() {
        return loggedInUser != null;
    }
}
