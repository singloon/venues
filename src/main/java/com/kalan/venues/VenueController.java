package com.kalan.venues;

import com.kalan.venues.model.RecommendationsResource;
import com.kalan.venues.service.VenueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Api(value = "venue", description = "the venue API")
public class VenueController {
    public static final String RECOMMENDATIONS = "/v1/recommendations";

    private final VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @RequestMapping(value = RECOMMENDATIONS, method = RequestMethod.GET, produces = {"application/hal+json"})
    @ApiOperation(value = "Finds Recommendations by venue", nickname = "findRecommendations", response = RecommendationsResource.class, responseContainer = "List" , tags={ "recommendations", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = RecommendationsResource.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid parameters provided") })
    public HttpEntity<RecommendationsResource> recommendations(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "venue") String venue) {

        RecommendationsResource recommendationsResource = ofNullable(venueService.retrieveRecommendations(location, venue))
                .map(r -> new RecommendationsResource(r.getMatch(), r.getVenues()))
                .orElse(new RecommendationsResource(null, emptyList()));

        recommendationsResource.add(linkTo(methodOn(VenueController.class).recommendations(location, venue)).withSelfRel());

        return new ResponseEntity<>(recommendationsResource, HttpStatus.OK);
    }
}
