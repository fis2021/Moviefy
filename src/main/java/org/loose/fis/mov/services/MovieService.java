package org.loose.fis.mov.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Screening;

import javax.xml.crypto.Data;
import java.util.List;

import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class MovieService {
    public static List<Movie> getMoviesNotScreenedAtCinema(Cinema cinema) {
        ObjectRepository<Movie> movieRepo = DatabaseService.getMovieRepo();
        List<Movie> retVal = movieRepo.find().toList();
        ObjectRepository<Screening> screeningRepo = DatabaseService.getScreeningRepo();

        for (Movie movie : movieRepo.find()) {
            if (screeningRepo.find(
                    and (
                            eq("cinemaName", cinema.getName()),
                            eq("movieName", movie.getTitle())
                    )
            ).firstOrDefault() != null) {
                retVal.remove(movie);
            }
        }

        return retVal;
    }
}
