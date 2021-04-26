package org.loose.fis.mov.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class Movie {
    @Id
    private String title;
    private String description;
    private int length;

    public Movie(String title, String description, int length) {
        this.title = title;
        this.description = description;
        this.length = length;
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
