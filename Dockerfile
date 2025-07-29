# ┌── Stage 1: Build with Maven and Node (for TailwindCSS)
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build

# Install Node.js and npm (needed for Tailwind)
RUN apk add --no-cache nodejs npm

WORKDIR /app

# Copy entire Maven wrapper directory and scripts
COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY pom.xml .

# Make wrapper executable
RUN chmod +x mvnw


# Now copy the full source
COPY src/ src/
COPY package.json package-lock.json ./

# Install node modules
RUN npm install
# Run Tailwind CSS build (ensure Tailwind config is present if needed)
RUN npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --minify

# Package the Spring Boot app without tests
RUN mvn clean install -DskipTests


# ┌── Stage 2: Runtime with lightweight JRE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the packaged jar from build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV OPENAI_API_KEY=""

ENTRYPOINT ["java", "-jar", "app.jar"]
