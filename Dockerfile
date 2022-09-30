FROM maven
COPY ./ ./
RUN mvn clean install
CMD echo "this image is used to build the jar files and is used as builder for other images"