FROM openjdk:8-jre-alpine

# Install curl and bash for the entry script
RUN apk --update add curl bash && \
  rm -rf /var/cache/apk/*

#RUN mkdir /opt
COPY ./target/*.jar /opt/app.jar
WORKDIR /opt
EXPOSE 8760-8770

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Xms256M", "-Xmx512M", "-jar","/opt/app.jar"]