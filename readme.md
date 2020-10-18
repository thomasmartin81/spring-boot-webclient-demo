# Spring Boot request/response demo with WebClient
This project contains three SpringBoot applications which communicate via REST.

## Applications
* Front (8080, BasicAuth)
* Middle (8090, BasicAuth)
* End (8091, no Auth)

## Flow
postman -> front -> middle -> end -> middle -> front -> postman
