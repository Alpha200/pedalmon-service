FROM azul/zulu-openjdk:21

WORKDIR /app

COPY build/libs/pedalmon-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8743

ENTRYPOINT ["java", "-jar", "app.jar"]
