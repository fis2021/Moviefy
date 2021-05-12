package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.exceptions.UniqueConstraintException;
import org.dizitart.no2.mapper.NitriteIdModule;
import org.junit.jupiter.api.*;
import org.loose.fis.mov.model.Review;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

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
    void findReviewsForMovie() {
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
    void getClientReview() {
    }

    @Test
    void updateReview() {
    }

    @Test
    void deleteReview() {
        //De adaugat un user in sesiune
        assertEquals(0,DatabaseService.getReviewRepo().size());
    }
}