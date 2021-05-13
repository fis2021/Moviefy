package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.exceptions.SessionAlreadyExistsException;
import org.loose.fis.mov.exceptions.TimeIntervalOccupiedException;
import org.loose.fis.mov.model.Booking;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.model.User;

import java.io.IOException;
import java.util.Date;

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
            throws TimeIntervalOccupiedException, CinemaAlreadyExistsException, SessionAlreadyExistsException, IOException {
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
    void addBooking() throws TimeIntervalOccupiedException, CinemaAlreadyExistsException, SessionAlreadyExistsException {
        Date date=new Date(12,12,2001);
        User user= new User("test","test","test","test","test","admin");
        SessionService.startSession(user);
        CinemaService.addCinema("test","test","test",4);
        ScreeningService.addScreening("test","test",3,date);

        Screening screening=ScreeningService.findScreeningByID(DatabaseService.getScreeningRepo().find().toList().get(0).getId());
        SessionService.setSelectedScreening(screening);
        Booking booking=new Booking(null,"test",SessionService.getSelectedScreening().getId(),2);
        BookingService.addBooking(booking);
        assertEquals(1,DatabaseService.getBookingRepo().size());
        assertEquals(2,DatabaseService.getBookingRepo().find().toList().get(0).getNumberOfSeats());
    }

    @Test
    void findUsersWithBookingAtScreening() {

    }

    @Test
    void findBookingofUser() throws SessionAlreadyExistsException, CinemaAlreadyExistsException, TimeIntervalOccupiedException {
        Date date=new Date(12,12,2001);
        User user= new User("test","test","test","test","test","admin");
        SessionService.startSession(user);
        CinemaService.addCinema("test","test","test",4);
        MovieService.addMovie("test","test",3);
        ScreeningService.addScreening("test","test",3,date);
        Screening screening=ScreeningService.findScreeningByID(DatabaseService.getScreeningRepo().find().toList().get(0).getId());
        SessionService.setSelectedScreening(screening);
        Booking booking=new Booking(null,"test",SessionService.getSelectedScreening().getId(),2);
        BookingService.addBooking(booking);

        assertEquals(booking,BookingService.findBookingofUser(user).get(0));
    }

    @Test
    void findBookingsAtScreening() throws SessionAlreadyExistsException, CinemaAlreadyExistsException, TimeIntervalOccupiedException {
        Date date=new Date(12,12,2001);
        User user= new User("test","test","test","test","test","admin");
        SessionService.startSession(user);
        CinemaService.addCinema("test","test","test",4);
        MovieService.addMovie("test","test",3);
        ScreeningService.addScreening("test","test",3,date);

        Screening screening=ScreeningService.findScreeningByID(DatabaseService.getScreeningRepo().find().toList().get(0).getId());
        SessionService.setSelectedScreening(screening);
        Booking booking=new Booking(null,"test",DatabaseService.getScreeningRepo().find().toList().get(0).getId(),2);
        BookingService.addBooking(booking);

        assertEquals(booking,BookingService.findBookingsAtScreening(screening).get(0));
    }

    @Test
    void deleteBooking() {
    }

    @Test
    void findBookingByID() {
    }
}