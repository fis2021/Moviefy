package org.loose.fis.mov.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;
import org.loose.fis.mov.model.Booking;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class BookingService {
    public static List<User> findUsersWithBookingAtScreening(Screening screening) {
        List<String> usernames = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Booking> bookings = DatabaseService.getBookingRepo().find(
                eq("screeningId", screening.getId())
        ).toList();

        bookings.forEach(booking -> usernames.add(booking.getClientName()));
        usernames.forEach(username -> {
            try {
                users.add(UserService.findUser(username));
            } catch (UserNotRegisteredException e) {
                e.printStackTrace();
            }
        });

        return users;
    }
}
