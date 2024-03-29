# Smart Meter: Account Management

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

Make sure, Docker is running.

Start Redis:
`docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest`


You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

This will spin up all necessary dependencies, using [Quarkus Dev Services](https://quarkus.io/guides/dev-services), like
a Postgres database. The database will be seeded using `seed.dev.sql`. In order for Quarkus to start the database,
Docker must already be started 

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Testing
Make sure, Docker is running.

Start Redis:
`docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest`

You can run the tests via the `quarkus test` command. Alternatively you can right-click on `src/test` and click on `Run tests in account-management`.

The Jacoco report is available at: `build/jacoco-report/index.html`
The test report can be found at: `build/reports/tests/test/index.html`

## Docker

Before building the container image run:
`./gradlew build`

Then, build the image with:
`docker build -f src/main/docker/Dockerfile.jvm -t quarkus/account-management-jvm .`

Then run the container using:
`docker run -i --rm -p 8080:8080 quarkus/account-management-jvm`

For developers of this project only:
Publish docker image:
- `./gradlew build`
- `docker build -f src/main/docker/Dockerfile.jvm -t bianca907/account-management .`
- `docker push bianca907/account-management`

## OpenAPI
The Swagger OpenAPI definition for this application is available at:
http://localhost:8080/swagger/