package org.loose.fis.mov.services;

import org.junit.jupiter.api.Test;
import org.loose.fis.mov.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class CommServiceTest {

    @Test
    void sendMail() {
        User user1 = new User(
                "test", "test", "test", "test_test", "ihedes13@gmail.com", "client");
        User user2 = new User(
                "test", "test", "test", "test_test", "ioan.hedes@student.upt.ro", "client");
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        assertDoesNotThrow(() -> CommService.sendMail(list, "Test e-mail", "This is a test e-mail"));
    }
}