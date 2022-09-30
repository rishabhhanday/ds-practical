FROM quoco-rmi_multimodulebuilder:latest AS builder

FROM openjdk:8-jre-alpine
ENV REMOTE_REFERENCE_NAME=dodydrivers
COPY --from=builder /dodgydrivers/target/dodgydrivers-*-jar-with-dependencies.jar /dodgydrivers.jar
CMD /usr/bin/java -jar /dodgydrivers.jar $REMOTE_REFERENCE_NAME

