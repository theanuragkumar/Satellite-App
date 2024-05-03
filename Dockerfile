FROM openjdk:17
EXPOSE 8082
COPY ./out/artifacts/questionBank_jar/questionBank.jar ROOT.jar
ENTRYPOINT ["java","-jar", "ROOT.jar"]