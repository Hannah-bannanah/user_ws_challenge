FROM openjdk:17-alpine

WORKDIR /usr/src/challenge

COPY ./target/challenge_jmp-0.0.1-SNAPSHOT.jar .

ENV PORT 8085

EXPOSE $PORT

CMD ["java","-jar","challenge_jmp-0.0.1-SNAPSHOT.jar"]