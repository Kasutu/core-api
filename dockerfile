FROM openjdk:11

WORKDIR /app

COPY ./target/core-api-0.0.1-SNAPSHOT.jar /app

EXPOSE 32615

CMD ["java", "-jar", "core-api-0.0.1-SNAPSHOT.jar"]