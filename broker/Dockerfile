FROM quoco-rmi_multimodulebuilder:latest AS builder

FROM openjdk:8-jre-alpine

ENV REMOTE_REFERENCE_NAME=broker
ENV QUOTATION_REMOTE_REFERENCE_NAMES=auldfellas,dodgydrivers,girlpower
ENV QUOTATION_REMOTE_REGISTRY_HOSTS=auldfellas,dodgydrivers,girlpower

COPY --from=builder /broker/target/broker-*-jar-with-dependencies.jar /broker.jar

CMD /usr/bin/java -jar /broker.jar $REMOTE_REFERENCE_NAME $QUOTATION_REMOTE_REFERENCE_NAMES $QUOTATION_REMOTE_REGISTRY_HOSTS

