package com.kalan.venues;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VenueController.class)
public class VenuesTest {

    private static final String RECOMMENDATIONS = VenueController.RECOMMENDATIONS;

    @Autowired
    private MockMvc mvc;

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
    @Ignore
    public void returnsRecommendedVenues() throws Exception {
        mvc.perform(get(RECOMMENDATIONS)
                .param("location", "london")
                .param("venue", "spitafields")
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

    private void hasJson(MvcResult result, String expected, String jsonPath) throws JSONException, UnsupportedEncodingException {
        JSONAssert.assertEquals(expected, JsonPath.parse(result.getResponse().getContentAsString()).read(jsonPath, String.class), JSONCompareMode.STRICT);
    }
}
