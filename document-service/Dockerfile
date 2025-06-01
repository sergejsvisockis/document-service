FROM openjdk:24-oracle
COPY ./target/document-service-1.0-SNAPSHOT.jar /usr/lib/contexts/document-service.jar
EXPOSE 80
CMD java \
        -DACCESS_KEY=${ACCESS_KEY} \
        -DSECRET_KEY=${SECRET_KEY} \
        -DREGION=${REGION} \
        -DDYNAMODB_ENDPOINT=${DYNAMODB_ENDPOINT} \
        -DS3_ENDPOINT=${S3_ENDPOINT} \
        -DSNS_ENDPOINT=${SNS_ENDPOINT} \
        -jar /usr/lib/contexts/document-service.jar
