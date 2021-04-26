package org.loose.fis.mov.services;

import org.loose.fis.mov.exceptions.*;
import org.loose.fis.mov.model.User;

import java.util.Objects;
import java.util.regex.Pattern;

public class UserService {
    public static void addUser(String username, String firstname, String lastname, String password, String email, String role,
                               String cinemaName, String cinemaAddress, String cinemaCapacity) throws Exception {
        UserService.checkUserAlreadyExists(username);
        UserService.checkEmailAlreadyUsed(email);
        if (Objects.equals(role, "Admin")) {
            CinemaService.addCinema(cinemaName, username, cinemaAddress, Integer.parseInt(cinemaCapacity));
        }
        DatabaseService.getUserRepo().insert(new User(username, firstname, lastname, password, email, role));
    }

    private static void checkUserAlreadyExists(String username) throws UserAlreadyExistsException {
        for (User user : DatabaseService.getUserRepo().find()) {
            if (Objects.equals(username, user.getUsername())) {
                throw new UserAlreadyExistsException(username);
            }
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
