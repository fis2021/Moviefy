package org.loose.fis.mov.services;

import javafx.util.Pair;
import org.loose.fis.mov.exceptions.TimeIntervalOccupiedException;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.dizitart.no2.objects.filters.ObjectFilters.*;

public final class ScreeningService {
    public static List<Screening> findAllScreeningsForCinema(String cinemaName) {
        return DatabaseService.getScreeningRepo().find(
                eq("cinemaName", cinemaName)
        ).toList();
    }

    public static List<Screening> findAllScreeningsForCinema(Cinema cinema) {
        return DatabaseService.getScreeningRepo().find(
                eq("cinemaName", cinema.getName())
        ).toList();
    }

    public static List<Screening> findAllFutureScreeningsForCinema(String cinemaName) {
        return DatabaseService.getScreeningRepo().find(
                and(
                        eq("cinemaName", cinemaName),
                        gt("date", Calendar.getInstance().getTime())
                )
        ).toList();
    }

    public static List<Screening> findAllFutureScreeningsForCinema(Cinema cinema) {
        return DatabaseService.getScreeningRepo().find(
                and(
                        eq("cinemaName", cinema.getName()),
                        gt("date", Calendar.getInstance().getTime())
                )
        ).toList();
    }

    public static void deleteScreening(Screening screening) {
        DatabaseService.getScreeningRepo().remove(screening);
    }

    public static Screening addScreening(String movieTitle, String movieDescription, int movieLength, Date screeningDate)
            throws TimeIntervalOccupiedException {
        User user = SessionService.getLoggedInUser();
        // this line will be replaced after merging and integrating with MOV-37;
        //Cinema cinema = DatabaseService.getCinemaRepo().find(eq("adminUsername", user.getUsername())).firstOrDefault();
        Cinema cinema = CinemaService.findCinemaForAdmin(user);

        // verific data;
        if (checkIntervalOccupied(cinema, screeningDate, movieLength)) {
            throw new TimeIntervalOccupiedException();
        }

        // verific film duplicat;
        Movie movie = MovieService.getMovieByTitle(movieTitle);
        if (movie == null) {
            movie = MovieService.addMovie(movieTitle, movieDescription, movieLength);
        }

        // adaug proiectia;
        Screening screening = new Screening(null, screeningDate, movie.getTitle(), cinema.getName(), cinema.getCapacity());
        DatabaseService.getScreeningRepo().insert(screening);

        return screening;
    }

    /* Method for checking if there is a screening overlapping with a certain period of time */
    private static boolean checkIntervalOccupied(Cinema cinema, Date date, int length) {
        Calendar calendar = Calendar.getInstance();

        // setting the bounds for the interval to check;
        calendar.setTime(date);
        Date lowerMarginInterval = calendar.getTime();

        calendar.add(Calendar.MINUTE, length);
        Date upperMarginInterval = calendar.getTime();

        // checking if there are any screenings going on in that interval;
        for (Screening screening : DatabaseService.getScreeningRepo().find().toList()) {
            Movie movie = MovieService.getMovieForScreening(screening);

            // setting the bounds for the found screening interval;
            calendar.setTime(screening.getDate());
            Date lowerMarginScreening = calendar.getTime();
            calendar.add(Calendar.MINUTE, movie.getLength());
            Date upperMarginScreening = calendar.getTime();

            // checking if the intervals are overlapping;
            if (CommService.areIntervalsOverlapping(
                    new Pair<>(lowerMarginInterval, upperMarginInterval),
                    new Pair<>(lowerMarginScreening, upperMarginScreening)
            )) {
                return true;
            }
        }
        return false;
    }
}
