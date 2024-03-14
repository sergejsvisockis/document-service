FROM openjdk:17-oracle
COPY target/document-read-service-1.0-SNAPSHOT-fat.jar /usr/lib/contexts/document-read-service.jar
CMD java ${JAVA_OPTS} -jar /usr/lib/contexts/document-read-service.jar