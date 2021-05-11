package org.loose.fis.mov.services;

import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.User;

import java.util.List;
import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public final class CinemaService {
    protected static Cinema addCinema(String name, String adminUsername, String address, int capacity)
            throws CinemaAlreadyExistsException {
        if (CinemaService.existsCinema(name)) {
            throw new CinemaAlreadyExistsException(name);
        }
        Cinema cinema = new Cinema(name, adminUsername, address, capacity);
        DatabaseService.getCinemaRepo().insert(cinema);
        return cinema;
    }

    public static Cinema findCinemaForAdmin(String username)  {
        return DatabaseService.getCinemaRepo().find(
                eq("adminUsername", username)
        ).firstOrDefault();
    }

    public static Cinema findCinemaForAdmin(User admin) {
        return DatabaseService.getCinemaRepo().find(
                eq("adminUsername", admin.getUsername())
        ).firstOrDefault();
    }
    public static Cinema findCinemaByName(String name) {
        Cinema cinema = DatabaseService.getCinemaRepo().find(
                eq("name", name)
        ).firstOrDefault();
        return cinema;
    }

     static boolean existsCinema(String name) {
        for (Cinema cinema : DatabaseService.getCinemaRepo().find()) {
            if (Objects.equals(name, cinema.getName())) {
                return true;
            }
        }
        return false;
    }

    public static List<Cinema> getAllCinema() {
        return DatabaseService.getCinemaRepo().find().toList();
    }

}
