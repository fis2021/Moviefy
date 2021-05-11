package org.loose.fis.mov.services;

import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Review;

import java.util.List;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public final class ReviewService {

    public static List<Review> findReviewsForMovie(String MovieTitle){

        return DatabaseService.getReviewRepo().find(
                eq("movieTitle", MovieTitle)
        ).toList();
    }
}
