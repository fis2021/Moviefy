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
    public static void addUser(String username, String firstname, String lastname, String password, String email, String role,
                               String cinemaName, String cinemaAddress, String cinemaCapacity) throws Exception {
        UserService.checkUserAlreadyExists(username);
        UserService.checkEmailAlreadyUsed(email);
        if (Objects.equals(role, "Admin")) {
            CinemaService.addCinema(cinemaName, username, cinemaAddress, Integer.parseInt(cinemaCapacity));
        }
        DatabaseService.getUserRepo().insert(new User(username, firstname, lastname,
                UserService.encodePassword(username, password), email, role));
    }

    public static List<User> getAllUsers() {
        return DatabaseService.getUserRepo().find().toList();
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
    public static User findUserByEmail(String email) throws UserNotRegisteredException{
    User user= DatabaseService.getUserRepo().find(eq("email",email)).firstOrDefault();
    if (user==null){
        throw new UserNotRegisteredException();
    }
    return user;
    }
}
