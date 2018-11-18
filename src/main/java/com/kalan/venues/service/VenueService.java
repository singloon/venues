package com.kalan.venues.service;

import com.kalan.venues.model.Recommendations;

public interface VenueService {
    Recommendations retrieveRecommendations(String location, String venue);
}
