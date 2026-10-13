#FROM maven:3.8.3-openjdk-17

#WORKDIR /app

#COPY ./target/Ecommerce-0.0.1-SNAPSHOT.jar .

#EXPOSE 8080

#CMD ["java", "-jar", "Ecommerce-0.0.1-SNAPSHOT.jar"]



FROM maven:3.8.3-openjdk-17

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD [ "./mvnw", "spring-boot:run" ]
