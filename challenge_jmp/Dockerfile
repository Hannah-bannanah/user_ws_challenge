## DEV DOCKERFILE
FROM maven:3.8-openjdk-17-slim

WORKDIR /usr/src/challenge

COPY . .

RUN mvn clean install -DskipTests

CMD ["sh", "init.sh"]

## PROD DOCKERFILE
#FROM openjdk:17-alpine
#
#WORKDIR /usr/src/challenge
#
#COPY ./target/challenge_jmp-0.0.1-SNAPSHOT.jar .
#
#ENV PORT 8085
#
#EXPOSE $PORT
#
#CMD ["java","-jar","challenge_jmp-0.0.1-SNAPSHOT.jar"]