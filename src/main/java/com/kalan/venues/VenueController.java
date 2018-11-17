package com.kalan.venues;

import com.kalan.venues.model.Recommendations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class VenueController {
    public static final String RECOMMENDATIONS = "/v1/recommendations";

    @RequestMapping(value = RECOMMENDATIONS, method = RequestMethod.GET, produces = {"application/hal+json"})
    public HttpEntity<Recommendations> recommendations(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "venue") String venue) {

        Recommendations recommendations = new Recommendations(new ArrayList<>());
        recommendations.add(linkTo(methodOn(VenueController.class).recommendations(location, venue)).withSelfRel());

        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }
}
