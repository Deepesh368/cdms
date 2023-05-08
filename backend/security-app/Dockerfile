FROM openjdk:17
#COPY ./target/gateway-0.0.1-SNAPSHOT.jar ./
#WORKDIR ./
#CMD ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]
EXPOSE 9100
ADD ./target/security-app-0.0.1-SNAPSHOT.jar ./security-app.jar
ENTRYPOINT ["java", "-jar", "security-app.jar"]