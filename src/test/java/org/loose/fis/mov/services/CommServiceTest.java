package org.loose.fis.mov.services;


import javafx.util.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.model.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CommServiceTest {

    @Test
    @DisplayName("Check if the password format validator works")
    void isPasswordValid() {
        assertFalse(CommService.isPasswordValid("short"));
        assertTrue(CommService.isPasswordValid("long_pas"));
    }

    @Test
    @DisplayName("Check if the email format validator works")
    void isEmailValid() {
        assertFalse(CommService.isEmailValid("test"));
        assertFalse(CommService.isEmailValid("test@"));
        assertFalse(CommService.isEmailValid("@test"));
        assertFalse(CommService.isEmailValid("test@test"));
        assertFalse(CommService.isEmailValid("test@test."));
        assertFalse(CommService.isEmailValid("test.test"));
        assertTrue(CommService.isEmailValid("test@test.test"));
        assertTrue(CommService.isEmailValid("test@test.test.test"));
    }

    @Test
    @DisplayName("Test if the word generator can produce different words")
    void wordGenerator() {
        String a = CommService.WordGenerator((int) (100 * Math.random()));
        String b = CommService.WordGenerator((int) (100 * Math.random()));
        String c = CommService.WordGenerator((int) (100 * Math.random()));
        assertNotEquals(a, b);
        assertNotEquals(b, c);
    }

    @Test
    @DisplayName("Test if the checker for interval overlapping works")
    void areIntervalsOverlapping() {
        Pair<Date, Date> intervalA = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 10, 0)
                        .getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 14, 0)
                        .getTime()
        );
        Pair<Date, Date> intervalB = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 9, 0)
                        .getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 11, 0)
                        .getTime()
        );
        Pair<Date, Date> intervalC = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 13, 0)
                        .getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 15, 0)
                        .getTime()
        );
        Pair<Date, Date> intervalD = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 11, 0)
                        .getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 13, 0)
                        .getTime()
        );
        Pair<Date, Date> intervalE = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 9, 0)
                        .getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 15, 0)
                        .getTime()
        );
        Pair<Date, Date> intervalF = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 10, 0)
                        .getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 14, 0)
                        .getTime()
        );
        Pair<Date, Date> intervalG = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 26, 10, 0)
                        .getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 26, 14, 0)
                        .getTime()
        );

        assertTrue(CommService.areIntervalsOverlapping(
                intervalA,
                intervalB
        )); // overlapping on beginning of A;
        assertTrue(CommService.areIntervalsOverlapping(
                intervalA,
                intervalC
        )); // overlapping on end of A;
        assertTrue(CommService.areIntervalsOverlapping(
                intervalA,
                intervalD
        )); // overlapping A contains D
        assertTrue(CommService.areIntervalsOverlapping(
                intervalA,
                intervalE
        )); // overlapping A is contained by E
        assertTrue(CommService.areIntervalsOverlapping(
                intervalA,
                intervalF
        )); // A contains G;
        assertFalse(CommService.areIntervalsOverlapping(
                intervalA,
                intervalG
        )); // not overlapping
    }

    @Test
    void isDateInThePast() {
        Date inTheFuture = new GregorianCalendar(
                2099,
                Calendar.DECEMBER,
                25,
                13,
                37
        ).getTime();
        Date inThePast = new GregorianCalendar(2009, Calendar.MAY, 3, 17, 58)
                .getTime();
        assertTrue(CommService.isDateInThePast(inThePast));
        assertFalse(CommService.isDateInThePast(inTheFuture));
    }

    @Test
    void extractTime() {
        Date date = new GregorianCalendar(
                2099,
                Calendar.DECEMBER,
                25,
                22,
                31
        ).getTime();
        assertEquals("22:31", CommService.extractTime(date));
    }

    @Test
    void extractDate() {
        Date date = new GregorianCalendar(
                2099,
                Calendar.DECEMBER,
                25
        ).getTime();
        assertEquals("25/12/2099", CommService.extractDate(date));
    }

    @Test
    @DisplayName("Test if single recipient mail works")
    void sendMail() {
        assertDoesNotThrow(() -> CommService.sendMail("ihedes13@gmail.com",
                                                      "Test e-mail",
                                                      "This is a test e-mail"
        ));

    }

    @Test
    @DisplayName("Test if it is possible to send mails to multiple users")
    void sendMailMultiple() {
        User user1 = new User(
                "test",
                "test",
                "test",
                "test_test",
                "ihedes13@gmail.com",
                "client"
        );
        User user2 = new User(
                "test",
                "test",
                "test",
                "test_test",
                "ioan.hedes@student.upt.ro",
                "client"
        );
        List<User> list = new ArrayList<>();
        assertDoesNotThrow(() -> CommService
                .sendMail(list, "Test e-mail", "This is a test e-mail"));
        list.add(user1);
        assertDoesNotThrow(() -> CommService
                .sendMail(list, "Test e-mail", "This is a test e-mail"));
        list.add(user2);
        assertDoesNotThrow(() -> CommService
                .sendMail(list, "Test e-mail", "This is a test e-mail"));
    }
}
