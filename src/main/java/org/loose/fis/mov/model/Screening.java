package org.loose.fis.mov.model;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.util.Date;
import java.util.Objects;

public class Screening {
    @Id
    private NitriteId id;
    private final Date date;
    private final Movie movie;
    private final Cinema cinema;
    private int remainingCapacity;

    public Screening(Date date, Movie movie, Cinema cinema) {
        this.date = date;
        this.movie = movie;
        this.cinema = cinema;
        this.remainingCapacity = cinema.getCapacity();
    }

    public NitriteId getId() {
        return id;
    }

    public void setId(NitriteId id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public Movie getMovie() {
        return movie;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return Objects.equals(date, screening.date) && Objects.equals(movie, screening.movie) && Objects.equals(cinema, screening.cinema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, movie, cinema);
    }
}
