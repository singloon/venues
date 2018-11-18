package com.kalan.venues.service;

import com.kalan.venues.model.Credentials;
import com.kalan.venues.model.foursquare.explore.ExploreResult;
import com.kalan.venues.model.foursquare.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static java.lang.String.valueOf;

@Service
public class FourSquareClient {
    private static final String SEARCH = "https://api.foursquare.com/v2/venues/search";
    private static final String EXPLORE = "https://api.foursquare.com/v2/venues/explore";

    private final RestTemplate restTemplate;
    private final Credentials credentials;

    @Autowired
    public FourSquareClient(RestTemplate restTemplate, Credentials credentials) {
        this.restTemplate = restTemplate;
        this.credentials = credentials;
    }

    public SearchResult search(String location, String venue) {
        return restTemplate.exchange(
                url(SEARCH, credentials)
                        .queryParam("query", venue)
                        .queryParam("near", location)
                        .queryParam("limit", "1")
                        .toUriString(),
                HttpMethod.GET,
                headers(),
                SearchResult.class).getBody();
    }

    public ExploreResult explore(Double lat, Double lng) {
        return restTemplate.exchange(
                url(EXPLORE, credentials)
                        .queryParam("ll", latLong(lat, lng))
                        .toUriString(),
                HttpMethod.GET,
                headers(),
                ExploreResult.class).getBody();
    }

    private String latLong(Double lat, Double lng) {
        return String.format("%s,%s", valueOf(lat), valueOf(lng));
    }

    private UriComponentsBuilder url(String url, Credentials credentials) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("client_id", credentials.getClientId())
                .queryParam("client_secret", credentials.getSecret())
                .queryParam("v", "20180323");
        return builder;
    }

    private HttpEntity<?> headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

        return new HttpEntity<>(headers);
    }
}
