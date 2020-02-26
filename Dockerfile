FROM java:8
EXPOSE 8082
ADD /target/NZP-OnlineReport-StaticUi-Service-0.0.1-SNAPSHOT.jar NZP-OnlineReport-StaticUi-Service.jar
ENTRYPOINT ["java", "-jar", "NZP-OnlineReport-StaticUi-Service.jar"]