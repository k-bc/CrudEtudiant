# ======= STAGE 1 : BUILD =======
FROM maven:3.8.6-openjdk-17 AS build

WORKDIR /app

# Copier seulement les fichiers Maven d’abord (cache Docker)
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Copier le code source
COPY src ./src

# Build sans tests
RUN mvn clean package -DskipTests

# ======= STAGE 2 : RUN =======
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier le JAR généré
COPY --from=build /app/target/*jar app.jar

EXPOSE 8089

ENTRYPOINT ["java","-jar","app.jar"]


