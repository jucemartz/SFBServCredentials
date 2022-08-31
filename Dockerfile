FROM lottus.azurecr.io/cv-entities-base:0.1.0-unstable.131 as builder
WORKDIR /app/SFBServCredentials
COPY . /app/SFBServCredentials
RUN mvn clean install -DskipTests
RUN $JAVA_HOME/bin/keytool -list -v -alias "ldap_ula" -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit

FROM openjdk:latest
WORKDIR /app
COPY --from=builder /app/SFBServCredentials/credentials-api/target/ /app/

EXPOSE 5150
ENTRYPOINT ["java","-jar","/app/credentials-api-0.1.0-SNAPSHOT.jar"]
