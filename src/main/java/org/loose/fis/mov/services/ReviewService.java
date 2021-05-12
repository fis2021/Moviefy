package org.loose.fis.mov.services;

import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Review;
import org.loose.fis.mov.model.User;

import java.util.List;

import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public final class ReviewService {

    public static List<Review> findReviewsForMovie(String MovieTitle){

        return DatabaseService.getReviewRepo().find(
                eq("movieTitle", MovieTitle)
        ).toList();
    }
    public static void addReview(Review review){
        DatabaseService.getReviewRepo().insert(review);
        DatabaseService.getMovieRepo().update(SessionService.getSelectedMovie());
    }

    public static Review getClientReview(String username, String movieTitle){
        return DatabaseService.getReviewRepo().find(
                and(
                        eq("clientUsername", username),
                        eq("movieTitle", movieTitle)
                )
        ).firstOrDefault();
    }
}
