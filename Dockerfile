#
# Build stage
#
FROM maven:3.5-jdk-8-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage WORKDIR /home/app
#
FROM openjdk:8-jdk-alpine
EXPOSE 8082

COPY --from=build /home/app/target/NZP-OnlineReport-StaticUi-Service-0.0.1-SNAPSHOT.jar /usr/local/lib/NZP-OnlineReport-StaticUi-Service.jar
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /usr/local/lib/NZP-OnlineReport-StaticUi-Service.jar"]
