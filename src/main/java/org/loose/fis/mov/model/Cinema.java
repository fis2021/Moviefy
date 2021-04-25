package org.loose.fis.mov.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class Cinema {
    @Id
    private String name;
    private String adminName;
    private String address;
    private int capacity;

    public Cinema(String name, String adminName, String address, int capacity) {
        this.name = name;
        this.adminName = adminName;
        this.address = address;
        this.capacity = capacity;
    }

    public Cinema() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(name, cinema.name) && Objects.equals(address, cinema.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}
