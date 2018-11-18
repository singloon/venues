package com.kalan.venues.model.foursquare.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {
    private final SearchResponse searchResponse;

    @JsonCreator
    public SearchResult(@JsonProperty("response") SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
    }

    public SearchResponse getSearchResponse() {
        return searchResponse;
    }
}
