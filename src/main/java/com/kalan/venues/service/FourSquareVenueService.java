package com.kalan.venues.service;

import com.kalan.venues.model.Recommendations;
import com.kalan.venues.model.Venue;
import com.kalan.venues.model.foursquare.explore.ExploreResponse;
import com.kalan.venues.model.foursquare.explore.ExploreResult;
import com.kalan.venues.model.foursquare.explore.Group;
import com.kalan.venues.model.foursquare.explore.Item;
import com.kalan.venues.model.foursquare.search.SearchResponse;
import com.kalan.venues.model.foursquare.search.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.kalan.venues.model.Recommendations.recommendations;
import static com.kalan.venues.model.Venue.venue;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
public class FourSquareVenueService implements VenueService {
    private final Logger logger = LoggerFactory.getLogger(FourSquareVenueService.class);
    private final FourSquareClient fourSquareClient;

    @Autowired
    public FourSquareVenueService(FourSquareClient fourSquareClient) {
        this.fourSquareClient = fourSquareClient;
    }

    @Override
    public Recommendations retrieveRecommendations(String location, String venue) {
        SearchResult search = fourSquareClient.search(location, venue);

        Optional<com.kalan.venues.model.foursquare.search.Venue> returnedVenue = ofNullable(search).map(SearchResult::getSearchResponse)
                .map(SearchResponse::getVenues)
                .flatMap(v -> v.stream().findFirst());

        if (!returnedVenue.isPresent()) {
            logger.info("Could not find any matching venues for {}, location {}", venue, location);
            return recommendations(null, emptyList());
        }
        Optional<ExploreResult> returnedExploreResult = returnedVenue
                .map(com.kalan.venues.model.foursquare.search.Venue::getLocation)
                .map(coordinates -> fourSquareClient.explore(coordinates.getLat(), coordinates.getLng()));

        Venue match = venue(returnedVenue.get());

        if (!returnedExploreResult.isPresent()) {
            logger.info("Could not find any recommendations for {}", venue);
            return recommendations(match, emptyList());
        }

        List<com.kalan.venues.model.foursquare.explore.Venue> returnedVenues = returnedExploreResult
                .map(ExploreResult::getExploreResponse)
                .map(ExploreResponse::getGroups)
                .map(group ->
                        group.stream()
                                .map(Group::getItems)
                                .filter(Objects::nonNull)
                                .flatMap(items -> items.stream()
                                        .map(Item::getVenue))
                                .filter(Objects::nonNull)
                                .collect(toList())).orElse(emptyList());


        return recommendations(match, mapVenues(returnedVenues));
    }

    private List<Venue> mapVenues(List<com.kalan.venues.model.foursquare.explore.Venue> returnedVenues) {
        return returnedVenues.stream().map(Venue::venue).collect(toList());
    }

}
