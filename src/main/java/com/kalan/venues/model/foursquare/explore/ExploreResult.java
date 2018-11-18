package com.kalan.venues.model.foursquare.explore;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExploreResult {
    private final ExploreResponse exploreResponse;

    @JsonCreator
    public ExploreResult(@JsonProperty("response") ExploreResponse exploreResponse) {
        this.exploreResponse = exploreResponse;
    }

    public ExploreResponse getExploreResponse() {
        return exploreResponse;
    }
}
