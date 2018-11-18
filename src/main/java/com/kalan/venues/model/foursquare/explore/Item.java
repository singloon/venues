package com.kalan.venues.model.foursquare.explore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private final Venue venue;

    public Item(@JsonProperty("venue") Venue venue) {
        this.venue = venue;
    }

    public Venue getVenue() {
        return venue;
    }
}
