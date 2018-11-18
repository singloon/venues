package com.kalan.venues.model.foursquare.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Venue {
    private final String id;
    private final Location location;
    private final String name;

    @JsonCreator
    public Venue(@JsonProperty("id") String id, @JsonProperty("location") Location location, @JsonProperty("name") String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
