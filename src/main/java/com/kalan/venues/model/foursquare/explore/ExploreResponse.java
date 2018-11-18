package com.kalan.venues.model.foursquare.explore;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExploreResponse {
    private final Integer totalResults;
    private final List<Group> groups;

    @JsonCreator
    public ExploreResponse(@JsonProperty("totalResults") Integer totalResults, @JsonProperty("groups") List<Group> groups) {
        this.totalResults = totalResults;
        this.groups = groups;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
