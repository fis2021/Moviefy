package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.exceptions.UniqueConstraintException;
import org.dizitart.no2.mapper.NitriteIdModule;
import org.junit.jupiter.api.*;
import org.loose.fis.mov.exceptions.SessionAlreadyExistsException;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Review;
import org.loose.fis.mov.model.User;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;
//@Disabled
class ReviewServiceTest {

    @BeforeAll
    static void beforeAll() {
        FileSystemService.setApplicationFolder("moviefy_test");
        FileSystemService.initDirectory();
    }
    @AfterAll
    static void afterAll()
            throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
    }
    @BeforeEach
    void setUp()
            throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
        DatabaseService.initDatabase();

    }

    @AfterEach
    void tearDown() {
        DatabaseService.closeDatabase();
        SessionService.destroySession();
    }
    @Test
    @DisplayName("Test if we can find a review for a movie")
    void findReviewsForMovie() {
        Review review=new Review(null,"test","test","test");
        ReviewService.addReview(review);
       assertEquals("test",ReviewService.findReviewsForMovie("test").get(0).getText());

    }

    @Test
    @DisplayName("Test if reviews are added correctly")
    void addReview() {
        Review review=new Review(null,"test","test","test");
        ReviewService.addReview(review);
        assertEquals(1,DatabaseService.getReviewRepo().size());
        //trebuie verificat daca in baza de date e review cum trebe
        assertEquals("test",DatabaseService.getReviewRepo().find().firstOrDefault().getClientUsername());
        assertEquals("test",DatabaseService.getReviewRepo().find().firstOrDefault().getMovieTitle());
        assertEquals("test",DatabaseService.getReviewRepo().find().firstOrDefault().getText());
    }
    @Test
    @DisplayName("Test if Reviews get updated")
    void getClientReview() {
        Review review=new Review(null,"test","test","test");
        ReviewService.addReview(review);
        assertEquals("test",ReviewService.getClientReview("test","test").getClientUsername());
        assertEquals("test",ReviewService.getClientReview("test","test").getText());
        assertEquals("test",ReviewService.getClientReview("test","test").getMovieTitle());
    }

    @Test
    @DisplayName("Test if Reviews get updated")
    void updateReview() {
        Review review=new Review(null,"test","test","test");
        ReviewService.addReview(review);
        ReviewService.updateReview(review,"test1");
        assertEquals("test1",review.getText());
    }

    @Test
    @DisplayName("Test if Reviews get deleted")
    void deleteReview() throws SessionAlreadyExistsException {
        User user= new User("test","test","test","test","test","Client");
        SessionService.startSession(user);
        MovieService.addMovie("test","test",1);
        Movie movie=new Movie("test","test",1);
        SessionService.setSelectedString(movie);
        Review review=new Review(null,"test","test","test");
        ReviewService.addReview(review);
        ReviewService.deleteReview();
        assertEquals(0,DatabaseService.getReviewRepo().size());
    }
}