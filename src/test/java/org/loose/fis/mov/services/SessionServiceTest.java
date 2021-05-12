package org.loose.fis.mov.services;

import org.junit.jupiter.api.*;
import org.loose.fis.mov.exceptions.SessionAlreadyExistsException;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.model.User;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        SessionService.destroySession();
    }

    @Test
    @DisplayName("Test if sessions are created correctly")
    void startSession() {
        User user = new User("test", "test", "test", "test", "test", "test");
        assertDoesNotThrow(() -> SessionService.startSession(new User(
                "test",
                "test",
                "test",
                "test",
                "test",
                "test"
        )));
        assertEquals(SessionService.getLoggedInUser(), user);
    }

    @Test
    @DisplayName("Test if two sessions cannot coexist")
    void startSessionTwo() {
        assertDoesNotThrow(() -> SessionService.startSession(new User(
                "test",
                "test",
                "test",
                "test",
                "test",
                "test"
        )));
        assertThrows(
                SessionAlreadyExistsException.class,
                () -> SessionService.startSession(new User(
                        "test2",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test"
                ))
        );

    }

    @Test
    @DisplayName("Test if sessions are erased correctly")
    void destroySession() {
        assertDoesNotThrow(() -> SessionService.startSession(
                new User(
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test"
                )));
        SessionService.destroySession();
        assertNull(SessionService.getLoggedInUser());
    }

    @Test
    @DisplayName("Test if selected screenings are set correctly")
    void setSelectedScreening() {
        Screening screening = new Screening(null,
                                            new GregorianCalendar(
                                                    2021,
                                                    Calendar.JANUARY,
                                                    1,
                                                    13,
                                                    37
                                            ).getTime(),
                                            "test_movie",
                                            "test_cinema",
                                            10
        );
        SessionService.setSelectedScreening(screening);
        assertEquals(screening, SessionService.getSelectedScreening());
    }
}