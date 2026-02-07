# ====== Build stage ======
FROM maven:3.8.1-openjdk-8 AS builder

WORKDIR /app

# Copier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Copier le code source et construire l'application
COPY src ./src
RUN mvn clean package -DskipTests

# ====== Runtime stage ======
FROM openjdk:8-jre-slim

WORKDIR /app

# Installer curl pour le healthcheck
RUN apt-get update \
 && apt-get install -y curl \
 && rm -rf /var/lib/apt/lists/*

# Copier le JAR construit de l'étape précédente
COPY --from=builder /app/target/crudEtudiant-0.0.1-SNAPSHOT.jar app.jar

# Port exposé (correspondant à server.port=8089)
EXPOSE 8089

# Healthcheck utilisant Spring Boot Actuator
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8089/actuator/health || exit 1

# Point d'entrée pour lancer l'application
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]

