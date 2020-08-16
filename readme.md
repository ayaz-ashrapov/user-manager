#### About

This is a simple web-service that allows you to create, update and delete user entries.

The service is built using Java, Spring Boot. ElasticSearch is used as a storage.



#### Prerequisites
JDK 8+, Gradle, Docker (Docker-Engine 19.0.3+), Docker Compose.
#### Build
Execute 'gradle build' to build the project from sources.

#### Run

Execute 'docker-compose up'. By default it will start the app on http://localhost:7777.

#### Limitations

Elasticsearch container doesn't use a filesystem for persistent storage. Modify the docker-compose.yml and add volumes if
you want to change the behavior.