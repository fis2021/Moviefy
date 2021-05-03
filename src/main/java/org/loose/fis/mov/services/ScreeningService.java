package org.loose.fis.mov.services;

import javafx.util.Pair;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Screening;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;

import static org.dizitart.no2.objects.filters.ObjectFilters.*;

public class ScreeningService {
    /* Method for checking if there is a screening overlapping with a certain period of time */
    public static boolean checkIntervalOccupied(Cinema cinema, Date date, int length) {
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
