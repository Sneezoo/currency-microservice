# Currency Microservice

Simply fetches EUR exchange rates and stores them in-memory.

Retrieve them with
 - `http://localhost:8080/currencies`
 - `http://localhost:8080/currencies/{my-currency}`

## Build

`mvn install`

## Development

You can run this microservice with `docker compose up`, but run `mvn install` before.