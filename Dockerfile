FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ADD $JAR_FILE /currencies/currencies.jar
ENTRYPOINT ["java","-jar","/currencies/currencies.jar"]