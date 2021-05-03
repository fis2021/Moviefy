package org.loose.fis.mov.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.model.Cinema;

import java.util.Objects;

public class CinemaService {
    public static Cinema addCinema(String name, String adminUsername, String address, int capacity)
            throws CinemaAlreadyExistsException {
        CinemaService.checkCinemaAlreadyExists(name);
        Cinema cinema = new Cinema(name, adminUsername, address, capacity);
        DatabaseService.getCinemaRepo().insert(cinema);
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
