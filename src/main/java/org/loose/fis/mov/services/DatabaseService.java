package org.loose.fis.mov.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.mov.model.*;

public final class DatabaseService {
    private static ObjectRepository<User> userRepo;
    private static ObjectRepository<Cinema> cinemaRepo;
    private static ObjectRepository<Movie> movieRepo;
    private static ObjectRepository<Screening> screeningRepo;
    private static ObjectRepository<Booking> bookingRepo;
    private static ObjectRepository<Review> reviewRepo;
    private static Nitrite database;

    public static void initDatabase() {
        database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("moviefy_test.db").toFile())
                .openOrCreate();
        userRepo = database.getRepository(User.class);
        cinemaRepo = database.getRepository(Cinema.class);
        movieRepo = database.getRepository(Movie.class);
        screeningRepo = database.getRepository(Screening.class);
        bookingRepo = database.getRepository(Booking.class);
        reviewRepo = database.getRepository(Review.class);
    }

    public static void closeDatabase() {
        database.close();
    }

    protected static ObjectRepository<User> getUserRepo() {
        return userRepo;
    }

    protected static ObjectRepository<Cinema> getCinemaRepo() {
        return cinemaRepo;
    }

    protected static ObjectRepository<Movie> getMovieRepo() {
        return movieRepo;
    }

    protected static ObjectRepository<Screening> getScreeningRepo() {
        return screeningRepo;
    }

    protected static ObjectRepository<Booking> getBookingRepo() {
        return bookingRepo;
    }

    protected static ObjectRepository<Review> getReviewRepo() {
        return reviewRepo;
    }
}
