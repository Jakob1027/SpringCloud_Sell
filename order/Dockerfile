FROM hub.c.163.com/library/java:8-alpine
ADD order-server/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]