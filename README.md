# Parking Lots API

## About

Parking Lots API is spring boot application which load large amount of data and exposes endpoints for searching
through parking lots. For data loading Spring Batch is used because of chunk-based processing, parallel processing and much more.
For data persistence Elasticsearch is used because of its fast data retrieval. 

2 endpoints are implemented. First one for retrieving the nearest parking lot for given latitude and longitude.
Second one is for retrieving parking lots in 1km radius for given latitude and longitude.

### Implementation details

- For Primary Key UUID field is added. Another way would be to use composite PK derived from multiple fields.
- Search is done for any Year and any Type (Parking/Home). Possible improvement would be search by specific Year 
and specific Type.
- NOTE! Because of UUID field added, on application startup elasticsearch should be recreated because elements will be duplicated.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 17
- Spring Boot
- Docker
- Elasticsearch

### Installing

- Run Elasticsearch on docker using docker-compose. Navigate to /docker folder and run:

```
docker compose up
```

- or just run the command:

```
docker run -d --name es762 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2

```

- Then run application using preferred IDE or run next command

```
./mvnw spring-boot:run
```

### Future steps

- Add integration tests using JUnit and Testcontainers
- Increase test coverage to at least 80%
- Extend docker-compose to run both Elasticsearch and API
