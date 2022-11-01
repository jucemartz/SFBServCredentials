FROM lottus.azurecr.io/cv-entities-base:0.1.0-unstable.154 as builder
WORKDIR /app/SFBServCredentials
COPY . /app/SFBServCredentials
RUN mvn clean install -DskipTests

FROM openjdk:latest
WORKDIR /app
COPY --from=builder /app/SFBServCredentials/credentials-api/target/ /app/

EXPOSE 5150
ENTRYPOINT ["java","-jar","/app/credentials-api-0.1.0-SNAPSHOT.jar"]
