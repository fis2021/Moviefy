package org.loose.fis.mov.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.mov.model.*;

import static org.loose.fis.mov.services.FileSystemService.getPathToFile;

public class DatabaseService {
    private static ObjectRepository<User> userRepo;
    private static ObjectRepository<Cinema> cinemaRepo;
    private static ObjectRepository<Movie> movieRepo;
    private static ObjectRepository<Screening> screeningRepo;
    private static ObjectRepository<Booking> bookingRepo;
    private static ObjectRepository<Review> reviewRepo;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("moviefy_test.db").toFile())
                .openOrCreate();

        userRepo = database.getRepository(User.class);
        cinemaRepo = database.getRepository(Cinema.class);
        movieRepo = database.getRepository(Movie.class);
        screeningRepo = database.getRepository(Screening.class);
        bookingRepo = database.getRepository(Booking.class);
        reviewRepo = database.getRepository(Review.class);
    }
}