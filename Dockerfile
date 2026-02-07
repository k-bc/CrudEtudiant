FROM maven:3.8.1-openjdk-17-slim as build

WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Étape 2: Créer l'image légère pour l'exécution
FROM openjdk:17-jdk-slim

WORKDIR /app
# Copier le JAR construit de l'étape précédente
COPY --from=build /app/target/crudEtudiant-0.0.1-SNAPSHOT.jar app.jar

# Port exposé (correspondant à server.port=8089)
EXPOSE 8089
# Point d'entrée pour lancer l'application
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]

