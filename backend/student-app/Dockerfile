FROM openjdk:17
#COPY ./target/gateway-0.0.1-SNAPSHOT.jar ./
#WORKDIR ./
#CMD ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]
EXPOSE 9200
ADD ./target/student-app-0.0.1-SNAPSHOT.jar ./student-app.jar
ENTRYPOINT ["java", "-jar", "student-app.jar"]