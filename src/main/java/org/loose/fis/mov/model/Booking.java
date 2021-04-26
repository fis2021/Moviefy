package org.loose.fis.mov.model;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.util.Date;
import java.util.Objects;

public class Booking {
    @Id
    private NitriteId id;
    private String clientName;
    private NitriteId screeningId;
    private int numberOfSeats;

    public Booking(NitriteId id, String clientName, NitriteId screeningId, int numberOfSeats) {
        this.id = id;
        this.clientName = clientName;
        this.screeningId = screeningId;
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

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public NitriteId getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(NitriteId screeningId) {
        this.screeningId = screeningId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(clientName, booking.clientName) && Objects.equals(screeningId, booking.screeningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientName, screeningId);
    }
}
