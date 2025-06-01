FROM openjdk:24-oracle
COPY ./target/document-service-1.0-SNAPSHOT.jar /usr/lib/contexts/document-service.jar
EXPOSE 80
CMD java -DACCESS_KEY=${ACCESS_KEY} -DSECRET_KEY=${SECRET_KEY} -jar /usr/lib/contexts/document-service.jar
