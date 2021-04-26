package org.loose.fis.mov.services;

import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.exceptions.EmailAddressAlreadyUsedException;
import org.loose.fis.mov.exceptions.PasswordTooWeakException;
import org.loose.fis.mov.exceptions.UserAlreadyExistsException;
import org.loose.fis.mov.model.User;

import java.util.Objects;

public class UserService {
    private static final int MIN_PASSWORD_LENGTH = 8;

    public static void addUser(String username, String firstname, String lastname, String password, String email, String role)
            throws UserAlreadyExistsException, PasswordTooWeakException, EmailAddressAlreadyUsedException {
        UserService.checkUserAlreadyExists(username);
        UserService.checkMinimumPasswordStrength(password);
        UserService.checkEmailAlreadyUsed(email);
        DatabaseService.getUserRepo().insert(new User(username, firstname, lastname, password, email, role));
    }

    public static void addUser(String username, String firstname, String lastname, String password, String email, String role,
                        String cinemaName, String cinemaAddress, int cinemaCapacity)
            throws UserAlreadyExistsException, PasswordTooWeakException, EmailAddressAlreadyUsedException,
            CinemaAlreadyExistsException {
        UserService.checkUserAlreadyExists(username);
        UserService.checkMinimumPasswordStrength(password);
        UserService.checkEmailAlreadyUsed(email);
        CinemaService.addCinema(cinemaName, username, cinemaAddress, cinemaCapacity);
        DatabaseService.getUserRepo().insert(new User(username, firstname, lastname, password, email, role));
    }

    private static void checkUserAlreadyExists(String username) throws UserAlreadyExistsException {
        for (User user : DatabaseService.getUserRepo().find()) {
            if (Objects.equals(username, user.getUsername())) {
                throw new UserAlreadyExistsException(username);
            }
        }
    }

    private static void checkMinimumPasswordStrength(String password) throws PasswordTooWeakException {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new PasswordTooWeakException();
        }
    }

    private static void checkEmailAlreadyUsed(String email) throws EmailAddressAlreadyUsedException {
        for (User user : DatabaseService.getUserRepo().find()) {
            if (Objects.equals(email, user.getEmail())) {
                throw new EmailAddressAlreadyUsedException(email);
            }
        }
    }
}
