package org.loose.fis.mov.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class Movie {
    @Id
    private String name;
    private String description;
    private int length;

    public Movie(String name, String description, int length) {
        this.name = name;
        this.description = description;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
