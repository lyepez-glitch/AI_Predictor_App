# ┌── Stage 1: Build with Maven and Node (for TailwindCSS)
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build

# Install Node.js and npm (needed for Tailwind)
RUN apk add --no-cache nodejs npm

WORKDIR /app

# Copy only necessary files first (better caching)
COPY pom.xml mvnw .mvn/ ./

# Pre-fetch dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Now copy source and frontend assets
COPY src/ ./src/

# Run Tailwind build
RUN npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --minify

# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests

# ┌── Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Allow port binding
EXPOSE 8080

# Set OpenAI API Key as env variable (Render will inject it)
ENV OPENAI_API_KEY=""

ENTRYPOINT ["java", "-jar", "app.jar"]
