package com.kalan.venues.model;

import java.util.List;

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
}
