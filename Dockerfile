FROM openjdk:17-jdk-slim
COPY target/credit-0.0.1-SNAPSHOT.jar credit-0.0.1.jar
ENTRYPOINT ["java","-jar","/credit-0.0.1.jar"]