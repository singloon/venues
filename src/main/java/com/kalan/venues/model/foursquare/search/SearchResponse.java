package com.kalan.venues.model.foursquare.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
    private final List<Venue> venues;

    @JsonCreator
    public SearchResponse(@JsonProperty("venues") List<Venue> venue) {
        this.venues = venue;
    }

    public List<Venue> getVenues() {
        return venues;
    }
}
