FROM java:8
EXPOSE 8082
ADD /target/NZP-OnlineReport-StaticUi-Service.jar NZP-OnlineReport-StaticUi-Service.jar
ENTRYPOINT ["java", "-jar", "NZP-OnlineReport-StaticUi-Service.jar"]