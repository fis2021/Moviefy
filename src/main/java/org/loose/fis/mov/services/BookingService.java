package org.loose.fis.mov.services;

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
        List<Booking> bookings = findBookingsAtScreening(screening);

        bookings.forEach(booking -> usernames.add(booking.getClientName()));
        usernames.forEach(username -> {
            users.add(UserService.findUser(username));
        });

        return users;
    }

    public static List<Booking> findBookingsAtScreening(Screening screening) {
        return DatabaseService.getBookingRepo().find(
                eq("screeningId", screening.getId())
        ).toList();
    }
}
