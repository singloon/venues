# Venues

App to return venue recommendations based around the venue that the user has inputted.

## To run the app:

You must provide the foursquare client api and secret via Environment Variables:
`CLIENT_ID`

`CLIENT_SECRET`

Or else the app will not start.

Requires Java 8.

`./gradlew build && java -jar build/libs/venues-0.0.1-SNAPSHOT.jar`


## Example
### Swagger
To access swagger, you'll be redirected if you hit
`http:localhost:8080`

### Recommendations
Query `http:localhost:8080/search?location=london&query=spitafields`

Response:
```
{
    "match": {
        "id": "101",
        "name": "Spitafields",
        "location": {
            "lat": 51.516513,
            "lng": -0.07288
        }
    },
    "recommendations": [{
            "id": "123",
            "name": "Beer O'Clock",
            "location": {
                "address": "79 Enid St",
                "lat": 51.497517,
                "lng": -0.072148,
                "postalCode": "SE16 3RA",
                "cc": "GB",
                "city": "London",
                "state": "Greater London",
                "country": "United Kingdom"
            },
            "type": "brewery"
        },
        {
            "id": "456",
            "name": "Brown Cup",
            "location": {
                "address": "55 Waterloo St",
                "lat": 57.497517,
                "lng": -0.092148,
                "postalCode": "SE26 6YA",
                "cc": "GB",
                "city": "London",
                "state": "Greater London",
                "country": "United Kingdom"
            },
            "type": "cafe"
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost/search?location=london&venue=spitafields"
        }
    }
}
```

## Approach

1. Making usage of the foursquare search API we first search for the venue via the user input.
2. Once we get the result we then search for recommendations via the explore endpoint with the lat long of the result.

- Choose to make client id and client secret available via environment variables, this suits all tooling with containers e.g. k8s.
- Response is hal/json which will become more useful when have more endpoints, pagination and embedded models.
- All the JSON is modeled via Jackson annotated POJOs, so we can apply all business logic via objects.
- Constructor injection in case we need to test as POJOs.
- Venue Service interface in case we need to swap out for another provider for recommendations.
- We have VenuesTest which gives us a high level test which allows us to test the rest endpoint and the JSON deserialization/serialization.
- The business logic is tested in the FourSquareVenueServiceTest.

### Future/TODO

1. Pagination and limits for recommendations (we get the total results in response).
2. Mark the VenueTest.java as Acceptance Test.
3. Metrics endpoint if to be deployed into prod.
