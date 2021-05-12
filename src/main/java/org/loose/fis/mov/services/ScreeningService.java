package org.loose.fis.mov.services;

import javafx.util.Pair;
import org.dizitart.no2.NitriteId;
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

    public static Screening findScreeningByID(NitriteId id){
        return DatabaseService.getScreeningRepo().find(
                eq("id", id) ).firstOrDefault();

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

        // verific film duplicat;
        Movie movie = MovieService.findMovieByTitle(movieTitle);
        if (movie == null) {
            movie = MovieService.addMovie(movieTitle, movieDescription, movieLength);
        }

        // verific data;
        if (checkIntervalOccupied(cinema, screeningDate, movie.getLength())) {
            throw new TimeIntervalOccupiedException();
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
