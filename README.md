[![Build Status](https://travis-ci.com/digid0c/kafka-app-demo.svg?branch=master)](https://travis-ci.com/github/digid0c/kafka-app-demo)
[![codecov](https://codecov.io/gh/digid0c/kafka-app-demo/branch/master/graph/badge.svg?token=81OP0FB9MG)](https://codecov.io/gh/digid0c/kafka-app-demo)

# Kafka communication demo application

* [Requirements](#requirements)
* [Build](#build)
* [Usage](#usage)
* [Further development](#further-development)

# Requirements
* Java 11 or higher
* Maven 3.6 or higher (optional, there is a Maven wrapper provided along with the app)
* Docker + Docker Compose

# Build

Use the following commands to build and run the app via Docker Compose:
* `mvn clean package`
* `docker-compose up --build`

In case you do not have Maven installed, you can use Maven wrapper provided along with the app.
In order to do that, just replace all `mvn` commands with `mvnw`.

# Usage
When app is up, go to http://localhost:8088/swagger-ui.html and start exploring the API. Use
Open API client provided, Postman or any other tool to send requests.

DB connection credentials may be found in `docker-compose.yml` file.

# Further development
* *CI/CD, deployment, repositories:* split multi-module monolith into 3 different projects with
separate repositories and CI/CD processes. Integration module plays shared library role i.e. it
should be stored in an artifact repository as well. Docker Compose will be no longer necessary.
* *Kafka configuration:* get rid of programmatic configuration and find a way to inject properties
from any properties file. One possible solution is to provide your own implementation of Spring's
`EnvironmentPostProcessor` (see `ConfigFileApplicationListener` for more details). Make use of more
than one topic, using partitions could be a good idea too.
* *External app configuration:* when monolith app is split into different repositories and Docker
Compose is gone, make use of independent Docker containers. Each container should have bounded
volume attached where it can take external configuration file from and run it as part of `jar`
command e.g. `--spring.config.location=file:/path/to/volume/application.properties`.
* *Security, load balancing:* enable authentication mechanism for Kafka and Zookeeper. Make Kafka
use encrypted security protocol instead of plaintext one. Using HTTPS protocol for client-server
communication would be a plus. Apache/Nginx can be used as a load balancer and/or rate limiter
proxy. Do not keep any credentials in repository (may be solved by previous step).
* *Resilience:* use any good containers orchestration tool like Kubernetes. Services health
monitoring is quite important too.
* *Lost message, double message:* configure Kafka retry mechanism policy. Send correlation IDs
along with events to enable consumer to check that each received event is unique.  
* *Misc:* improve logging by enabling rolling files policy. Make use of logs aggregators (ELK, Kafka)
to improve monitoring. Reuse correlation IDs from previous step to enable distributed tracing across
the whole system.