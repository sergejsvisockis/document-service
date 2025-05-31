FROM openjdk:17-oracle
COPY target/document-service-1.0-SNAPSHOT-fat.jar /usr/lib/contexts/document-service.jar
CMD java ${JAVA_OPTS} -jar /usr/lib/contexts/document-service.jar
