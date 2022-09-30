FROM quoco-rmi_multimodulebuilder:latest AS builder

FROM openjdk:8-jre-alpine

ENV BROKER_REMOTE_REFERENCE_NAME=broker
ENV BROKER_REGISTRY_HOST=broker
COPY --from=builder /client/target/client-*-jar-with-dependencies.jar /client.jar
CMD /usr/bin/java -jar /client.jar $BROKER_REMOTE_REFERENCE_NAME $BROKER_REGISTRY_HOST

