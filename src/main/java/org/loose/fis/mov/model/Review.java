package org.loose.fis.mov.model;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.util.Objects;

public final class Review {
    @Id
    private NitriteId id;
    private String clientUsername;
    private String movieTitle;
    private String text;

    public Review(NitriteId id, String clientUsername, String movieTitle, String text) {
        this.id = id;
        this.clientUsername = clientUsername;
        this.movieTitle = movieTitle;
        this.text = text;
    }

    public Review() {
    }

    public NitriteId getId() {
        return id;
    }

    public void setId(NitriteId id) {
        this.id = id;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        return Objects.equals(clientUsername, review.clientUsername) && Objects.equals(movieTitle, review.movieTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientUsername, movieTitle);
    }
}
