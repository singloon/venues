package com.kalan.venues.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class Recommendations extends ResourceSupport {
    @JsonProperty("recommendations")
    private final List<Venue> venues;

    public Recommendations(List<Venue> venues) {
        this.venues = venues;
    }

    public List<Venue> getVenues() {
        return venues;
    }
}
