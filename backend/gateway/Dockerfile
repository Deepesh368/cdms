FROM openjdk:17
#COPY ./target/gateway-0.0.1-SNAPSHOT.jar ./
#WORKDIR ./
#CMD ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]
EXPOSE 9090
ADD ./target/gateway-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]