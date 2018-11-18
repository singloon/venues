package com.kalan.venues.model.foursquare.explore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Venue {
    private final String id;
    private final String name;
    private final Location location;
    private final List<Category> categories;

    public Venue(@JsonProperty("id") String id, @JsonProperty("name") String name,
                 @JsonProperty("location") Location location, @JsonProperty("categories") List<Category> categories) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.categories = categories;
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

    public List<Category> getCategories() {
        return categories;
    }
}
