package org.loose.fis.mov.services;

import org.loose.fis.mov.exceptions.SessionAlreadyExistsException;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.model.User;

public final class SessionService {
    private static User loggedInUser;
    private static Screening selectedScreening;
    private static Movie selectedMovie;

    private SessionService() {

    }
    public static Movie getSelectedMovie(){return selectedMovie;}
    public static void setSelectedString(Movie movie){selectedMovie=movie;}

    public static void startSession(User user) throws SessionAlreadyExistsException {
        if (checkSessionExists()) {
            throw new SessionAlreadyExistsException();
        }
        loggedInUser = user;
    }

    public static void destroySession() {
        loggedInUser = null;
    }

    public static void setSelectedScreening(Screening screening) {
        selectedScreening = screening;
    }

    public static Screening getSelectedScreening(){
        return selectedScreening;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    private static boolean checkSessionExists() {
        return loggedInUser != null;
    }
}
