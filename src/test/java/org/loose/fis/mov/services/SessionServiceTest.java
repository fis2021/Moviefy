package org.loose.fis.mov.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.model.User;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class SessionServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLoggedInUser() {
        User user = new User("test", "test", "test", "test", "test", "test");
        assertDoesNotThrow(() -> SessionService.startSession(new User("test", "test", "test", "test", "test", "test")));
        assertEquals(SessionService.getLoggedInUser(), user);
    }
}