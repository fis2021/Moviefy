package org.loose.fis.mov.services;

import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class CommServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void areIntervalsOverlappingTest() {
        Pair<Date, Date> intervalA = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 10, 30).getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 14, 45).getTime()
        );
        Pair<Date, Date> intervalB = new Pair<>(
                new GregorianCalendar(2020, Calendar.MARCH, 25, 9, 21).getTime(),
                new GregorianCalendar(2020, Calendar.MARCH, 25, 10, 29).getTime()
        );
        Pair<Date, Date> intervalC = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 9, 59).getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 17, 24).getTime()
        );
        Pair<Date, Date> intervalD = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 12, 31).getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 14, 46).getTime()
        );
        Pair<Date, Date> intervalE = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 10, 30).getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 14, 45).getTime()
        );
        Pair<Date, Date> intervalF = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 9, 30).getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 11, 45).getTime()
        );
        Pair<Date, Date> intervalG = new Pair<>(
                new GregorianCalendar(2021, Calendar.MARCH, 25, 11, 30).getTime(),
                new GregorianCalendar(2021, Calendar.MARCH, 25, 13, 45).getTime()
        );

        assertFalse(CommService.areIntervalsOverlapping(intervalA, intervalB)); // not overlapping;
        assertTrue(CommService.areIntervalsOverlapping(intervalA, intervalC)); // C contains A;
        assertTrue(CommService.areIntervalsOverlapping(intervalA, intervalD)); // D contains end of A;
        assertTrue(CommService.areIntervalsOverlapping(intervalA, intervalE)); // E equals A;
        assertTrue(CommService.areIntervalsOverlapping(intervalA, intervalF)); // F contains start of A;
        assertTrue(CommService.areIntervalsOverlapping(intervalA, intervalG)); // A contains G;
    }
}