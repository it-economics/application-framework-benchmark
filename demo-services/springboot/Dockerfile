FROM eclipse-temurin:11-jre

ARG BOOT_JAR=./build/libs/app.jar
COPY ${BOOT_JAR} app.jar

#TODO add ENV variables for database etc.
ENTRYPOINT ["java","-jar","/app.jar"]