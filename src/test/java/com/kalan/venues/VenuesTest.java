package com.kalan.venues;

import com.jayway.jsonpath.JsonPath;
import com.kalan.venues.service.VenueService;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static com.kalan.venues.model.Location.Builder.location;
import static com.kalan.venues.model.Recommendations.recommendations;
import static com.kalan.venues.model.Venue.venue;
import static com.kalan.venues.model.Venue.venueWithLatLng;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VenueController.class)
@TestPropertySource(properties = {
        "CLIENT_ID=clientId",
        "CLIENT_SECRET=clientSecret"
})
public class VenuesTest {
    private static final String RECOMMENDATIONS = VenueController.RECOMMENDATIONS;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VenueService venueService;

    @Test
    public void returnsSelfLink() throws Exception {
        mvc.perform(get(RECOMMENDATIONS)
                .param("location", "london")
                .param("venue", "spitafields")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost/v1/recommendations?location=london&venue=spitafields")));
    }

    @Test
    public void returns400WhenMissingQuery() throws Exception {
        mvc.perform(get(RECOMMENDATIONS)
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void returnsRecommendedVenues() throws Exception {
        String location = "london";
        String venue = "spitafields";

        when(venueService.retrieveRecommendations(location, venue))
                .thenReturn(recommendations(venueWithLatLng("000", "spitafields", 50.1, -0.06), asList(
                        venue("123", "Beer O'Clock", location()
                                .withAddress("79 Enid St")
                                .withLat(51.497517)
                                .withLng(-0.072148)
                                .withPostalCode("SE16 3RA")
                                .withCc("GB")
                                .withCity("London")
                                .withState("Greater London")
                                .withCountry("United Kingdom")
                                .build(), "brewery"),
                        venue("456", "Brown Cup", location()
                                .withAddress("55 Waterloo St")
                                .withLat(57.497517)
                                .withLng(-0.092148)
                                .withPostalCode("SE26 6YA")
                                .withCc("GB")
                                .withCity("London")
                                .withState("Greater London")
                                .withCountry("United Kingdom")
                                .build(), "cafe"))));

        mvc.perform(get(RECOMMENDATIONS)
                .param("location", location)
                .param("venue", venue)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> hasJson(result, "[\n" +
                        "    {\n" +
                        "      \"id\": \"123\",\n" +
                        "      \"name\": \"Beer O'Clock\",\n" +
                        "      \"location\": {\n" +
                        "        \"address\": \"79 Enid St\",\n" +
                        "        \"lat\": 51.497517,\n" +
                        "        \"lng\": -0.072148,\n" +
                        "        \"postalCode\": \"SE16 3RA\",\n" +
                        "        \"cc\": \"GB\",\n" +
                        "        \"city\": \"London\",\n" +
                        "        \"state\": \"Greater London\",\n" +
                        "        \"country\": \"United Kingdom\"\n" +
                        "      },\n" +
                        "      \"type\": \"brewery\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"456\",\n" +
                        "      \"name\": \"Brown Cup\",\n" +
                        "      \"location\": {\n" +
                        "        \"address\": \"55 Waterloo St\",\n" +
                        "        \"lat\": 57.497517,\n" +
                        "        \"lng\": -0.092148,\n" +
                        "        \"postalCode\": \"SE26 6YA\",\n" +
                        "        \"cc\": \"GB\",\n" +
                        "        \"city\": \"London\",\n" +
                        "        \"state\": \"Greater London\",\n" +
                        "        \"country\": \"United Kingdom\"\n" +
                        "      },\n" +
                        "      \"type\": \"cafe\"\n" +
                        "    }\n" +
                        "  ]", "$.recommendations"));
    }

    @Test
    public void returnsMatch() throws Exception {
        String location = "london";
        String venue = "spitafields";

        when(venueService.retrieveRecommendations(location, venue))
                .thenReturn(recommendations(venueWithLatLng("101", "spitafields", 50.1, -0.06), emptyList()));

        mvc.perform(get(RECOMMENDATIONS)
                .param("location", location)
                .param("venue", venue)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.match.id", equalTo("101")))
                .andExpect(jsonPath("$.match.name", equalTo("spitafields")))
                .andExpect(result -> hasJson(result, "{\n" +
                "  \"lat\": 50.1,\n" +
                "  \"lng\": -0.06\n" +
                "}", "$.match.location"));
    }

    private void hasJson(MvcResult result, String expected, String jsonPath) throws JSONException, UnsupportedEncodingException {
        String response = JsonPath.read(result.getResponse().getContentAsString(), jsonPath).toString();
        JSONAssert.assertEquals(expected, response, JSONCompareMode.STRICT);
    }
}
