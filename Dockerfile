# ┌── Stage 1: Build with Maven and Node (for TailwindCSS)
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build

# Install Node.js and npm (needed for Tailwind)
RUN apk add --no-cache nodejs npm

WORKDIR /app

# Copy Maven wrapper scripts and config explicitly for wrapper to work
COPY mvnw mvnw.cmd ./
COPY .mvn/ .mvn/
COPY pom.xml ./

# Make mvnw executable and pre-fetch dependencies
RUN chmod +x mvnw && ./mvnw -e -X dependency:go-offline

# Now copy source code and resources
COPY src/ ./src/

# Run Tailwind CSS build
RUN npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --minify

# Build the Spring Boot app without running tests
RUN ./mvnw clean package -DskipTests

# ┌── Stage 2: Runtime with lightweight JRE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the packaged jar from build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the default port
EXPOSE 8080

# Set OpenAI API key environment variable (will be injected by Render)
ENV OPENAI_API_KEY=""

ENTRYPOINT ["java", "-jar", "app.jar"]
