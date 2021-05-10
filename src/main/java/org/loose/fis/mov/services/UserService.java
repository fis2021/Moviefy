package org.loose.fis.mov.services;

import org.loose.fis.mov.exceptions.*;
import org.loose.fis.mov.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public final class UserService {
    public static User addUser(String username, String firstname, String lastname, String password, String email, String role,
                               String cinemaName, String cinemaAddress, String cinemaCapacity) throws Exception {
        if (UserService.userAlreadyExists(username)) {
            throw new UserAlreadyExistsException(username);
        }
        if (UserService.emailAddressUsed(email)) {
            throw new EmailAddressAlreadyUsedException(email);
        }
        if (Objects.equals(role, "Admin")) {
            CinemaService.addCinema(cinemaName, username, cinemaAddress, Integer.parseInt(cinemaCapacity));
        }
        User user = new User(username, firstname, lastname, UserService.encodePassword(username, password), email, role);
        DatabaseService.getUserRepo().insert(user);
        return user;
    }

    public static List<User> getAllUsers() {
        return DatabaseService.getUserRepo().find().toList();
    }

    /* Returns the user as an Object so the app can redirect to the appropriate screen */
    public static User login(String username, String password) throws Exception {
        User user = findUser(username);
        if (user == null) {
            throw new UserNotRegisteredException();
        }
        checkPassword(user, password);
        SessionService.startSession(user);
        return user;
    }

    public static void logout() {
        SessionService.destroySession();
    }

    private static void changePassword(User user, String newPassword) {
        user.setPassword(encodePassword(user.getUsername(), newPassword));
        DatabaseService.getUserRepo().update(user);
    }

    /* this is used for changing the password before login */
    public static void changePasswordBeforeLogin(String email, String newPassword)
    throws UserNotRegisteredException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UserNotRegisteredException();
        }
        changePassword(user, newPassword);
    }

    /* this is used for changing the password after login */
    public static void changePasswordAfterLogin(String oldPassword, String newPassword)
    throws PasswordIncorrectException {
        User user = SessionService.getLoggedInUser();
        checkPassword(user, oldPassword);
        changePassword(user, newPassword);
    }

    public static User findUser(String username) {
        return DatabaseService.getUserRepo().find(eq("username", username)).firstOrDefault();
    }

    private static void checkPassword(User user, String password) throws PasswordIncorrectException {
        if (!Objects.equals(encodePassword(user.getUsername(), password), user.getPassword())) {
            throw new PasswordIncorrectException();
        }
    }

    private static boolean userAlreadyExists(String username) {
        for (User user : DatabaseService.getUserRepo().find()) {
            if (Objects.equals(username, user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    private static boolean emailAddressUsed(String email) {
        for (User user : DatabaseService.getUserRepo().find()) {
            if (Objects.equals(email, user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public static User findUserByEmail(String email) {
        return DatabaseService.getUserRepo().find(eq("email", email)).firstOrDefault();
    }
}
