FROM gradle:8.4.0-jdk17 as builder
MAINTAINER kh.com
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM openjdk:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/*.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/*.jar"]