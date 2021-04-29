package org.loose.fis.mov.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.exceptions.UserNotAdminException;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.User;

import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class CinemaService {
    public static void addCinema(String name, String adminUsername, String address, int capacity)
            throws CinemaAlreadyExistsException {
        CinemaService.checkCinemaAlreadyExists(name);
        DatabaseService.getCinemaRepo().insert(new Cinema(name, adminUsername, address, capacity));
    }

    public static Cinema findCinemaForAdmin(String username) throws UserNotAdminException {
        Cinema cinema = DatabaseService.getCinemaRepo().find(
                eq("adminUsername", username)
        ).firstOrDefault();

        if (cinema == null) {
            throw new UserNotAdminException();
        }
        return cinema;
    }

    public static Cinema findCinemaForAdmin(User admin) throws UserNotAdminException {
        Cinema cinema = DatabaseService.getCinemaRepo().find(
                eq("adminUsername", admin.getUsername())
        ).firstOrDefault();

        if (cinema == null) {
            throw new UserNotAdminException();
        }
        return cinema;
    }

    private static void checkCinemaAlreadyExists(String name)  throws CinemaAlreadyExistsException {
        for (Cinema cinema : DatabaseService.getCinemaRepo().find()) {
            if (Objects.equals(name, cinema.getName())) {
                throw new CinemaAlreadyExistsException(name);
            }
        }
    }
}
