FROM quoco-rmi_multimodulebuilder:latest AS builder

FROM openjdk:8-jre-alpine
ENV REMOTE_REFERENCE_NAME=girlpower
COPY --from=builder /girlpower/target/girlpower-*-jar-with-dependencies.jar /girlpower.jar
CMD /usr/bin/java -jar /girlpower.jar $REMOTE_REFERENCE_NAME

