FROM openjdk:17
EXPOSE 8082
COPY ./target/satellite-1.0.0.jar ROOT.jar
ENTRYPOINT ["java","-jar", "ROOT.jar"]