package org.loose.fis.mov.services;

import javafx.scene.control.PasswordField;
import org.loose.fis.mov.exceptions.EmailFormatInvalidException;
import org.loose.fis.mov.exceptions.EmptyFieldException;
import org.loose.fis.mov.exceptions.PasswordTooWeakException;
import org.loose.fis.mov.exceptions.UserAlreadyExistsException;
import org.loose.fis.mov.model.User;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Pattern;

public class CommService {

    private void checkEmailFormatValid(TextField emailField) throws EmailFormatInvalidException {
        Pattern emailPattern = Pattern.compile("^[A-Za-z1-9.]+@[A-Za-z1-9]+\\.[a-z]+$");
        if (!emailPattern.matcher(emailField.getText()).find()) {
            throw new EmailFormatInvalidException();
        }
    }

    private static void checkUserAlreadyExists(String username) throws UserAlreadyExistsException {
        for (User user : DatabaseService.getUserRepo().find()) {
            if (Objects.equals(username, user.getUsername())) {
                throw new UserAlreadyExistsException(username);
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
    private void checkMinimumPasswordStrength(PasswordField password) throws PasswordTooWeakException {
        if (password.getText().length() < 8) {
            throw new PasswordTooWeakException();
        }
    }

    public static String WordGenerator(int m){
        StringBuilder sb = new StringBuilder();

        String set ="1234567890-=][poiuytrewqasdfghjkl;'/.,mnbvcxzZXCVBNM,./';LKJHGFDSAQWERTYUIOP[]=-!@#$%^&*()zsefbhtsdo";

        for (int i= 0; i < m; i++) {
            int k = (int) (100*Math.random());
            sb.append(set.charAt(k));
        }
        String result = sb.toString();
        return result;
    }


}