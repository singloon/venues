package com.kalan.venues.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class RecommendationsResource extends ResourceSupport {
    private final Venue match;
    private final List<Venue> recommendations;

    public RecommendationsResource(@JsonProperty("match") Venue match, @JsonProperty("recommendations") List<Venue> venues) {
        this.match = match;
        this.recommendations = venues;
    }

    public Venue getMatch() {
        return match;
    }

    public List<Venue> getRecommendations() {
        return recommendations;
    }
}
