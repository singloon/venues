# Venues

App to return venue recommendations based around the venue that the user has inputted.

## To run the app:

You must provide the foursquare client api and secret via Environment Variables:
`CLIENT_ID`

`CLIENT_SECRET`

Or else the app will not start.

`./gradlew build && java -jar build/libs/venues-0.0.1-SNAPSHOT.jar`


## Example
Query `http:localhost:8080/search?location=london&query=spitafields`

Response:
```{
   "recommendations": [
     {
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
    "self": { "href": "http://localhost/search?location=london&venue=spitafields" }
   }
 }
```

## Approach

1. Making usage of the foursquare search API we first first search for the venue via the user input.

2. Once we get the result we then search for recommendations via the explore endpoint with the lat long of the result.

### Future/TODO

1. Add swagger.
2. De-dupe the original venue in case of dupe in recommendations.
3. Pagination and limits for recommendations (we get the total results in response).
4. Mark the VenueTest.java as Acceptance Test.


