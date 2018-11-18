package com.kalan.venues.model;

import java.util.Objects;

public class Location {
    private final String address;
    private final Double lat;
    private final Double lng;
    private final String postalCode;
    private final String cc;
    private final String city;
    private final String state;
    private final String country;

    private Location(String address, Double lat, Double lng, String postalCode, String cc, String city, String state, String country) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.postalCode = postalCode;
        this.cc = cc;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    private Location(Builder builder) {
        this.address = builder.address;
        this.lat = builder.lat;
        this.lng = builder.lng;
        this.postalCode = builder.postalCode;
        this.cc = builder.countryCode;
        this.city = builder.city;
        this.state = builder.state;
        this.country = builder.country;
    }

    public static Location location(com.kalan.venues.model.foursquare.explore.Location location) {
        return new Location(location.getAddress(), location.getLat(), location.getLng(),
                location.getPostalCode(), location.getCc(), location.getCity(), location.getState(),
                location.getCountry());
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

    public static class Builder {
        private String address;
        private Double lat;
        private Double lng;
        private String postalCode;
        private String countryCode;
        private String city;
        private String state;
        private String country;

        public static Builder location() {
            return new Builder();
        }

        public Builder withAddress(String address) {
            this.address = address;

            return this;
        }

        public Builder withLat(double lat) {
            this.lat = lat;

            return this;
        }

        public Builder withLng(double lng) {
            this.lng = lng;

            return this;
        }

        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;

            return this;
        }

        public Builder withCc(String countryCode) {
            this.countryCode = countryCode;

            return this;
        }

        public Builder withCity(String city) {
            this.city = city;

            return this;
        }

        public Builder withState(String state) {
            this.state = state;

            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;

            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(address, location.address) &&
                Objects.equals(lat, location.lat) &&
                Objects.equals(lng, location.lng) &&
                Objects.equals(postalCode, location.postalCode) &&
                Objects.equals(cc, location.cc) &&
                Objects.equals(city, location.city) &&
                Objects.equals(state, location.state) &&
                Objects.equals(country, location.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, state, lng, postalCode, cc, city, state, country);
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", postalCode='" + postalCode + '\'' +
                ", cc='" + cc + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
