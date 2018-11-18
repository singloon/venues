package com.kalan.venues.service;

import com.kalan.venues.model.Venue;

import java.util.List;

public interface VenueService {
    List<Venue> retrieveRecommendations(String location, String venue);
}
