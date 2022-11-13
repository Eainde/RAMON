# RAMON

## Build application
mvn clean install

## Run the application on local
- java -Dspring.profiles.active=test -jar target/app.1.0-SNAPSHOT.jar

## Run the application on local with docker
- docker compose up
- docker build -t ramon .
- docker run -t ramon

## Swagger docs
- http://localhost:8443/swagger-ui

