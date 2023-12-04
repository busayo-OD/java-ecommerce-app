FROM maven:3.6.3-jdk-11-slim AS build

WORKDIR usr/src/app

COPY . ./

RUN mvn clean install

FROM adoptopenjdk/openjdk11

WORKDIR /usr/src/app

EXPOSE 8082

COPY --from=build /usr/src/app/target/ecommerce-backend-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java","-jar", "./app.jar"]
