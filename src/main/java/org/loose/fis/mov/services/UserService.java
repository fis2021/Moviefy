package org.loose.fis.mov.services;

import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.mov.exceptions.*;
import org.loose.fis.mov.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class UserService {
    public static User addUser(String username, String firstname, String lastname, String password, String email, String role,
                               String cinemaName, String cinemaAddress, String cinemaCapacity) throws Exception {
        UserService.checkUserAlreadyExists(username);
        UserService.checkEmailAlreadyUsed(email);
        if (Objects.equals(role, "Admin")) {
            CinemaService.addCinema(cinemaName, username, cinemaAddress, Integer.parseInt(cinemaCapacity));
        }
        User user = new User(username, firstname, lastname,  UserService.encodePassword(username, password), email, role);
        DatabaseService.getUserRepo().insert(user);
        return user;
    }

    public static List<User> getAllUsers() {
        return DatabaseService.getUserRepo().find().toList();
    }

    /* Returns the user as an Object so the app can redirect to the appropriate screen */
    public static User login(String username, String password) throws Exception {
        User user = findUser(username);
        checkPassword(user, password);
        SessionService.startSession(user);
        return user;
    }

    public static void logout() throws SessionDoesNotExistException {
        SessionService.destroySession();
    }

    public static void changePassword(String email, String newPassword) throws UserNotRegisteredException {
        User user = findUserByEmail(email);
        user.setPassword(encodePassword(user.getUsername(), newPassword));
        DatabaseService.getUserRepo().update(user);
    }

    private static User findUser(String username) throws UserNotRegisteredException {
        User user = DatabaseService.getUserRepo().find(eq("username", username)).firstOrDefault();
        if (user == null) {
            throw new UserNotRegisteredException();
        }
        return user;
    }

    private static void checkPassword(User user, String password) throws PasswordIncorrectException {
        if (!Objects.equals(encodePassword(user.getUsername(), password), user.getPassword())) {
            throw new PasswordIncorrectException();
        }
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

    public static User findUserByEmail(String email) throws UserNotRegisteredException {
        User user = DatabaseService.getUserRepo().find(eq("email", email)).firstOrDefault();
        if (user == null) {
            throw new UserNotRegisteredException();
        }
        return user;
    }

}
