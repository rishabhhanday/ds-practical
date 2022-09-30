FROM quoco-rmi_multimodulebuilder:latest AS builder

FROM openjdk:8-jre-alpine
ENV REMOTE_REFERENCE_NAME=auldfellas
COPY --from=builder /auldfellas/target/auldfellas-*-jar-with-dependencies.jar /auldfellas.jar
CMD /usr/bin/java -jar /auldfellas.jar $REMOTE_REFERENCE_NAME

