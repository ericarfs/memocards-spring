# Etapa 1: Build usando Maven
FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências primeiro (cache eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Porta do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]