#!/bin/sh

mvn clean package -Dmaven.test.skip

java -jar ./target/challenge_jmp-0.0.1-SNAPSHOT.jar