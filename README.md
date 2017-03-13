# spring-jersey-jpa-demo

[![Build Status](https://travis-ci.org/sprint4us/spring-jersey-jpa-demo.svg?branch=master)](https://github.com/sprint4us/spring-jersey-jpa-demo)

![Intended Java technology stack](http://ibin.co/3EZ7vQAYb8cT.png)

---

>Done: Demo for JPA (EclipseLink)

>~~Todo: Spring (DI, AOP), Jersey (restful), Jetty (embedded, E2E) ...~~

>Manual test examples:

>$ mvn clean jetty:run

>$ curl --data France http://localhost:8080/demo/create/country

>$ curl -X PUT -d "id=1&l=English&p=39" http://localhost:8080/demo/update/country

>$ curl "http://localhost:8080/demo/search/percentage?c=France&l=English"

Trendy: Spring Boot, Spring Data (JPA, REST) ...

Alt: Dropwizard, Spark ...
