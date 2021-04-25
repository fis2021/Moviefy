package org.loose.fis.mov.model;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.util.Date;
import java.util.Objects;

public class Booking {
    @Id
    private NitriteId id;
    private String clientName;
    private Date screeningDate;
    private String cinemaName;
    private int numberOfSeats;

    public Booking(NitriteId id, String clientName, Date screeningDate, String cinemaName, int numberOfSeats) {
        this.id = id;
        this.clientName = clientName;
        this.screeningDate = screeningDate;
        this.cinemaName = cinemaName;
        this.numberOfSeats = numberOfSeats;
    }

    public Booking() {
    }

    public NitriteId getId() {
        return id;
    }

    public void setId(NitriteId id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(Date screeningDate) {
        this.screeningDate = screeningDate;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(clientName, booking.clientName) && Objects.equals(screeningDate, booking.screeningDate) && Objects.equals(cinemaName, booking.cinemaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientName, screeningDate, cinemaName);
    }
}
