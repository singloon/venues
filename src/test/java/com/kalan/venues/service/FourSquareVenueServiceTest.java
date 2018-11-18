package com.kalan.venues.service;

import com.kalan.venues.model.Recommendations;
import com.kalan.venues.model.Venue;
import com.kalan.venues.model.foursquare.explore.*;
import com.kalan.venues.model.foursquare.search.Location;
import com.kalan.venues.model.foursquare.search.SearchResponse;
import com.kalan.venues.model.foursquare.search.SearchResult;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.kalan.venues.Matchers.feature;
import static com.kalan.venues.model.Location.Builder.location;
import static com.kalan.venues.model.Recommendations.recommendations;
import static com.kalan.venues.model.Venue.venueWithLatLng;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FourSquareVenueServiceTest {
    private static final String LOCATION = "london";
    private static final String VENUE = "spitafields";

    private FourSquareClient fourSquareClient = mock(FourSquareClient.class);
    private FourSquareVenueService fourSquareVenueService = new FourSquareVenueService(fourSquareClient);

    @Test
    public void returnsFullRecommendations() {
        when(fourSquareClient.search(LOCATION, VENUE))
                .thenReturn(searchResult(matchingVenue("123", VENUE, new Location(10.0, 20.0))));

        when(fourSquareClient.explore(10.0, 20.0))
                .thenReturn(new ExploreResult(exploreResponse(
                        new Item(venue("456", "Brown Cup",
                                exploreLocation("123 Yellow Brick", 12.1, 20.5, "AAA BBB"),
                                "Coffee Shop")),
                        new Item(venue("789", "Beer O'Clock",
                                exploreLocation("555 Pub street", 12.5, 22.5, "CAC DOD"),
                                "Brewery")))));

        assertThat(fourSquareVenueService.retrieveRecommendations(LOCATION, VENUE), allOf(feature(Recommendations::getVenues, contains(
                Venue.venue("456", "Brown Cup", location()
                        .withAddress("123 Yellow Brick")
                        .withLat(12.1)
                        .withLng(20.5)
                        .withPostalCode("AAA BBB")
                        .withCc("GB")
                        .withCity("London")
                        .withState("London")
                        .withCountry("England").build(), "Coffee Shop"),
                Venue.venue("789", "Beer O'Clock", location()
                        .withAddress("555 Pub street")
                        .withLat(12.5)
                        .withLng(22.5)
                        .withPostalCode("CAC DOD")
                        .withCc("GB")
                        .withCity("London")
                        .withState("London")
                        .withCountry("England").build(), "Brewery"))),
                feature(Recommendations::getMatch, equalTo(venueWithLatLng("123", VENUE, 10.0, 20.0)))));
    }

    @Test
    public void returnsMatchWithNoRecommendations() {
        when(fourSquareClient.search(LOCATION, VENUE))
                .thenReturn(searchResult(matchingVenue("123", VENUE, new Location(10.0, 20.0))));

        assertThat(fourSquareVenueService.retrieveRecommendations(LOCATION, VENUE), equalTo(recommendations(venueWithLatLng("123", VENUE, 10.0, 20.0), emptyList())));
    }

    @Test
    public void returnsEmptyWhenNoMatch() {
        assertThat(fourSquareVenueService.retrieveRecommendations(LOCATION, VENUE), equalTo(recommendations(null, emptyList())));
    }

    @Test
    public void returnsMatchWithNoCoordinates() {
        when(fourSquareClient.search(LOCATION, VENUE))
                .thenReturn(searchResult(matchingVenue("123", VENUE, null)));

        assertThat(fourSquareVenueService.retrieveRecommendations(LOCATION, VENUE), feature(Recommendations::getMatch, equalTo(Venue.venue("123", VENUE, null, null))));
    }

    @Test
    public void returnsGroupWhenNoItems() {
        when(fourSquareClient.search(LOCATION, VENUE))
                .thenReturn(searchResult(matchingVenue("123", VENUE, new Location(10.0, 20.0))));

        when(fourSquareClient.explore(10.0, 20.0))
                .thenReturn(new ExploreResult(new ExploreResponse(2, singletonList(new Group(null)))));

        assertThat(fourSquareVenueService.retrieveRecommendations(LOCATION, VENUE), equalTo(recommendations(venueWithLatLng("123", VENUE, 10.0, 20.0), emptyList())));
    }

    @Test
    public void returnsEmptyGroupWhenItemsWithNoVenues() {
        when(fourSquareClient.search(LOCATION, VENUE))
                .thenReturn(searchResult(matchingVenue("123", VENUE, new Location(10.0, 20.0))));

        when(fourSquareClient.explore(10.0, 20.0))
                .thenReturn(new ExploreResult(new ExploreResponse(2, singletonList(new Group(singletonList(new Item(null)))))));

        assertThat(fourSquareVenueService.retrieveRecommendations(LOCATION, VENUE), equalTo(recommendations(venueWithLatLng("123", VENUE, 10.0, 20.0), emptyList())));
    }

    private ExploreResponse exploreResponse(Item... items) {
        return new ExploreResponse(2, singletonList(new Group(Arrays.asList(items))));
    }

    private com.kalan.venues.model.foursquare.explore.Venue venue(String id, String brown_cup, com.kalan.venues.model.foursquare.explore.Location aaa_bbb, String type) {
        return new com.kalan.venues.model.foursquare.explore.Venue(id, brown_cup, aaa_bbb, singletonList(new Category(type)));
    }

    private com.kalan.venues.model.foursquare.explore.Location exploreLocation(String address, double lat, double lng, String aaa_bbb) {
        return new com.kalan.venues.model.foursquare.explore.Location(address, lat, lng, 100, aaa_bbb, "GB", "London", "London", "England", null);
    }

    private SearchResult searchResult(List<com.kalan.venues.model.foursquare.search.Venue> spitafields) {
        return new SearchResult(new SearchResponse(spitafields));
    }

    private List<com.kalan.venues.model.foursquare.search.Venue> matchingVenue(String id, String venueName, Location location) {
        return singletonList(new com.kalan.venues.model.foursquare.search.Venue(id, location, venueName));
    }
}
