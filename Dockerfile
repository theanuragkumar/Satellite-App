FROM openjdk:17
EXPOSE 8082
COPY ./out/artifacts/satellite_jar/satellite.jar ROOT.jar
ENTRYPOINT ["java","-jar", "ROOT.jar"]