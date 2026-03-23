FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean install -DskipTests

CMD ["java", "-jar", "target/ecomplaintsportal-0.0.1-SNAPSHOT.jar"]
