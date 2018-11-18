package com.kalan.venues.model;

import com.kalan.venues.model.foursquare.explore.Category;

import java.util.Objects;

import static java.util.Optional.ofNullable;

public class Venue {
    private final String id;
    private final String name;
    private final Location location;
    private final String type;

    private Venue(String id, String name, Location location, String type) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
    }

    public static Venue venue(String id, String name, Location location, String type) {
        return new Venue(id, name, location, type);
    }

    public static Venue venue(com.kalan.venues.model.foursquare.explore.Venue returned) {
        return new Venue(returned.getId(), returned.getName(), location(returned), type(returned));
    }

    private static Location location(com.kalan.venues.model.foursquare.explore.Venue returned) {
        return Location.location(returned.getLocation());
    }

    private static String type(com.kalan.venues.model.foursquare.explore.Venue returned) {
         return ofNullable(returned.getCategories())
                .flatMap(categories -> categories.stream().findFirst())
                 .map(Category::getName)
                 .orElse("");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return Objects.equals(id, venue.id) &&
                Objects.equals(name, venue.name) &&
                Objects.equals(location, venue.location) &&
                Objects.equals(type, venue.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, type);
    }

    @Override
    public String toString() {
        return "Venue{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", type='" + type + '\'' +
                '}';
    }
}
