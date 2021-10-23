package com.example.myapplication.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Facility extends Location implements Serializable {
    private int id;
    private String name;
    private String url;
    private String address;
    private String postalCode;
    private String description;
    private Set<Sport> sports;

//    public Facility() {
//        super();
//    }

    public Facility(int id, String name, String url, String address, String postalCode,
                    String description, double latitude, double longitude) {
        super(latitude, longitude, name, LocationType.FACILITY);
        this.id = id;
        this.name = name;
        this.url = url;
        this.address = address;
        this.postalCode = postalCode;
        this.description = description;
        this.sports = new HashSet<>();
    }

    public Facility(int id, String name, String url, String address, String postalCode,
                    String description, Coordinates coordinates) {
        this(id, name, url, address, postalCode, description,
                coordinates.getLatitude(), coordinates.getLongitude());
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSports(Set<Sport> sports) {
        this.sports = sports;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getURL() {
        return url;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getDescription() {
        return description;
    }

    public Set<Sport> getSports() {
        return sports;
    }

    public void addSport(Sport sport) {
        sports.add(sport);
    }
    
    @Override
    public String toString() {
        String stringSports = (sports.isEmpty()) ? "NA"
                : sports.stream().map(Sport::getName)
                .collect(Collectors.joining(", "));
        return String.format(
                "Name: %s\nURL: %s\nAddress: %s\n"
                        + "Postal code: %s\nCoordinates: %s\nSports: %s\n",
                name, url, address, postalCode, getCoordinates(), stringSports);
    }
}
