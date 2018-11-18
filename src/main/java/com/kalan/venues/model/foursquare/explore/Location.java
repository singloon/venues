package com.kalan.venues.model.foursquare.explore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private final String address;
    private final Double lat;
    private final Double lng;
    private final Integer distance;
    private final String postalCode;
    private final String cc;
    private final String city;
    private final String state;
    private final String country;
    private final String crossStreet;

    public Location(@JsonProperty("address") String address, @JsonProperty("lat") Double lat, @JsonProperty("lng") Double lng, @JsonProperty("distance") Integer distance, @JsonProperty("postalCode") String postalCode, @JsonProperty("cc") String cc, @JsonProperty("city") String city, @JsonProperty("state") String state, @JsonProperty("country") String country, @JsonProperty("crossStreet") String crossStreet) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.postalCode = postalCode;
        this.cc = cc;
        this.city = city;
        this.state = state;
        this.country = country;
        this.crossStreet = crossStreet;
    }

    public String getAddress() {
        return address;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Integer getDistance() {
        return distance;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCc() {
        return cc;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getCrossStreet() {
        return crossStreet;
    }
}
