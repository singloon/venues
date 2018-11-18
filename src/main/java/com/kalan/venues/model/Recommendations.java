package com.kalan.venues.model;

import java.util.List;
import java.util.Objects;

public class Recommendations {
    private final Venue match;
    private final List<Venue> venues;

    private Recommendations(Venue match, List<Venue> venues) {
        this.match = match;
        this.venues = venues;
    }

    public static Recommendations recommendations(Venue match, List<Venue> recommendations) {
        return new Recommendations(match, recommendations);
    }

    public Venue getMatch() {
        return match;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendations that = (Recommendations) o;
        return Objects.equals(match, that.match) &&
                Objects.equals(venues, that.venues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(match, venues);
    }

    @Override
    public String toString() {
        return "Recommendations{" +
                "match=" + match +
                ", venues=" + venues +
                '}';
    }
}
