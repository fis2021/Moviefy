package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.mov.model.Booking;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    @BeforeAll
    static void beforeAll() {
        FileSystemService.setApplicationFolder("moviefy_test");
        FileSystemService.initDirectory();
    }
    @AfterAll
    static void afterAll()
            throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
    }
    @BeforeEach
    void setUp()
            throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
        DatabaseService.initDatabase();

    }

    @AfterEach
    void tearDown() {
        DatabaseService.closeDatabase();
        SessionService.destroySession();
    }

    @Test
    void addBooking() {
        Booking booking=new Booking(null,"test",null,2);

        BookingService.addBooking(booking);
        assertEquals(1,DatabaseService.getBookingRepo().size());
        assertEquals("test",DatabaseService.getBookingRepo().find().toList().get(0).getClientName());
        assertEquals(2,DatabaseService.getBookingRepo().find().toList().get(0).getNumberOfSeats());
    }

    @Test
    void findUsersWithBookingAtScreening() {
    }

    @Test
    void findBookingofUser() {
    }

    @Test
    void findBookingsAtScreening() {
    }

    @Test
    void deleteBooking() {
    }

    @Test
    void findBookingByID() {
    }
}