package org.loose.fis.mov.services;

import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Screening;

import java.util.List;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class MovieService {
    protected static Movie addMovie(String title, String description, int length) {
        Movie movie = new Movie(title, description, length);
        DatabaseService.getMovieRepo().insert(movie);
        return movie;
    }

    public static Movie getMovieForScreening(Screening screening) {
        return DatabaseService.getMovieRepo().find(
                eq("title", screening.getMovieTitle())
        ).firstOrDefault();
    }

    public static Movie findMovieByTitle(String title) {
        return DatabaseService.getMovieRepo().find(
                eq("title", title)
        ).firstOrDefault();
    }

    public static List<Movie> getAllMovies() {
        return DatabaseService.getMovieRepo().find().toList();
    }
}
