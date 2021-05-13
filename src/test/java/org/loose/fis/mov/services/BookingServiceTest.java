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
    @DisplayName("Testing if we can add bookings")
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
    @DisplayName("Testing if we can add find users by screening")
    void findUsersWithBookingAtScreening() throws Exception {
        Date date=new Date(12,12,2001);
        UserService.addUser("test","test","test","test","test","client","test","test","test");
        User user= UserService.findUser("test");
        SessionService.startSession(user);
        CinemaService.addCinema("test","test","test",4);
        MovieService.addMovie("test","test",3);
        ScreeningService.addScreening("test","test",3,date);
        Screening screening=ScreeningService.findScreeningByID(DatabaseService.getScreeningRepo().find().toList().get(0).getId());
        SessionService.setSelectedScreening(screening);
        Booking booking=new Booking(null,"test",SessionService.getSelectedScreening().getId(),2);
        BookingService.addBooking(booking);

        assertEquals("test",BookingService.findUsersWithBookingAtScreening(screening).get(0).getUsername());
        assertEquals("test",BookingService.findUsersWithBookingAtScreening(screening).get(0).getEmail());
        assertEquals("test",BookingService.findUsersWithBookingAtScreening(screening).get(0).getFirstname());
        assertEquals("test",BookingService.findUsersWithBookingAtScreening(screening).get(0).getLastname());
        assertEquals("client",BookingService.findUsersWithBookingAtScreening(screening).get(0).getRole());
    }

    @Test
    @DisplayName("Testing if we can add find bookings by users")
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
    @DisplayName("Testing if we can add find booking by screening")
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
    @DisplayName("Testing if we can delete bookings")
    void deleteBooking() throws Exception{
        Date date=new Date(12,12,2001);
        User user= new User("test","test","test","test","test","admin");
        SessionService.startSession(user);
        CinemaService.addCinema("test","test","test",4);
        ScreeningService.addScreening("test","test",3,date);
        Screening screening=ScreeningService.findScreeningByID(DatabaseService.getScreeningRepo().find().toList().get(0).getId());
        SessionService.setSelectedScreening(screening);
        Booking booking=new Booking(null,"test",SessionService.getSelectedScreening().getId(),2);
        BookingService.addBooking(booking);

        BookingService.deleteBooking(booking);
        assertEquals(0,DatabaseService.getBookingRepo().size());
    }

    @Test
    @DisplayName("Testing if we can add find bookings by id")
    void findBookingByID() throws Exception{

        Date date=new Date(12,12,2001);
        User user= new User("test","test","test","test","test","admin");
        SessionService.startSession(user);
        CinemaService.addCinema("test","test","test",4);
        ScreeningService.addScreening("test","test",3,date);
        Screening screening=ScreeningService.findScreeningByID(DatabaseService.getScreeningRepo().find().toList().get(0).getId());
        SessionService.setSelectedScreening(screening);
        Booking booking=new Booking(null,"test",SessionService.getSelectedScreening().getId(),2);
        BookingService.addBooking(booking);
        assertEquals(BookingService.findBookingByID(DatabaseService.getBookingRepo().find().toList().get(0).getId()).getId(),DatabaseService.getBookingRepo().find().toList().get(0).getId());
    }
}