package org.loose.fis.mov.services;

import javafx.util.Pair;
import org.loose.fis.mov.model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public final class CommService {
    public static boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    public static String WordGenerator(int m) {
        StringBuilder sb = new StringBuilder();

        String set = "1234567890-=][poiuytrewqasdfghjkl;'/.,mnbvcxzZXCVBNM,./';LKJHGFDSAQWERTYUIOP[]=-!@#$%^&*()zsefbhtsdo";

        for (int i = 0; i < m; i++) {
            int k = (int) (100 * Math.random());
            sb.append(set.charAt(k));
        }
        return sb.toString();
    }

    public static void sendMail(String recipient, String subject, String text) {
        MimeMessage message = createMailDraft();
        try {
            message.setFrom("Moviefy");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void sendMail(List<User> recipients, String subject, String text) {
        MimeMessage message = createMailDraft();
        try {
            message.setFrom("Moviefy");
            for (User recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient.getEmail()));
            }
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private static MimeMessage createMailDraft() {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");

        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("moviefycinemaservice", "z+.\\j]4Hg[");
            }
        });
        return new MimeMessage(session);
    }

    public static String extractTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format(
                "%2d:%2d",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
        ).replace(' ', '0');
    }

    public static String extractDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format(
                "%2d/%2d/%4d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)
        ).replace(' ', '0');
    }

    /* the A interval is where I want to insert a new screening / the B interval is an already existing screening */
    /* the key is the lower margin of the interval / the value is the upper margin of the interval */
    public static boolean areIntervalsOverlapping(Pair<Date, Date> intervalA, Pair<Date, Date> intervalB) {
        // case 1: interval B contains the beginning of interval A || interval B contains interval A;
        if (intervalA.getKey().compareTo(intervalB.getKey()) >= 0 && intervalA.getKey().compareTo(intervalB.getValue()) <= 0) {
            return true;
        }
        // case 2: interval B contains the end of interval A || interval B contains interval A;
        else if (intervalA.getValue().compareTo(intervalB.getKey()) >= 0 && intervalA.getValue().compareTo(intervalB.getValue()) <= 0) {
            return true;
        }
        // case 3: interval A contains interval B;
        else if (intervalA.getKey().compareTo(intervalB.getKey()) < 0 && intervalA.getValue().compareTo(intervalB.getValue()) > 0) {
            return true;
        }
        // case 4: the intervals are not overlapping;
        return false;
    }

    public static boolean isDateInThePast(Date date) {
        Date now = Calendar.getInstance().getTime();
        return (date.compareTo(now) < 0);
    }
}
