# Document Service

Let's imagine that the following service is a subset of the SOA platform that serves some business purpose let it be
insurance platform that generates some related documents.

## Functional requirements

* The following service has to generate these PDF documents:
    * Policy
    * Claim
    * Invoice
* All the documents have to be stored as both metadata and actual documents generated PDF in some storage (an
  Implementation shall allow switching from, for example, AWS S3 to SFTP. Also, assume that each
  document might be saved in a different storage)
* The same service is generating PDF documents
* There has to be a capability to download these documents as necessary from the storage
* Document metadata has to be saved in some storage
* The notification upon generation of each document has to be sent to some notification system (AWS SNS in this case)
* Each document has to contain the following set of fields:
    * Policy
        * Name of Insured
        * Name of the Owner
        * Policy number
        * Phone number
        * Address
        * City
        * Date of Birth
        * Relationship to Insured
        * Premium
        * LOB (Line of business)
    * Claim
        * Address
        * City
        * Policy number
        * Claim number
        * Place of incident
        * Short description
    * Invoice
        * Policy Number
          Name of Insured
        * Name of the Owner
        * Phone number
        * Total Price

## Non-functional requirements

* An AWS Cloud and its services will be used
* An app has to handle around 2-6 requests per second for both read and write
* An event of every operation shall be accessible in a messaging broker (AWS SNS)
* Document format - PDF - assume that in the future there might be different document formats as well
* Documents have to be stored in the AWS S3 bucket - assume that potentially in the future some of the entities might be
  stored in SFTP for example
* Document metadata will be store in a key-value NoSQL datastore AWS DynamoDB
* Notification (an event) on each operation (validate, generate, sendToStorage and save) have to be sent into the SNS (
  note that it has to be sent only after the full document lifecycle has complete)

## High-level design

![High level design](./high-level.png)

## Prepare an AWS services

Make DynamoDB table:

```shell
aws dynamodb create-table \
    --table-name document \
    --attribute-definitions AttributeName=documentId,AttributeType=S \
    --key-schema AttributeName=documentId,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
```

Make an SNS topic:

```shell
aws sns create-topic --name document-saved
```

An ARN has to be changed in an `application.yaml` or otherwise a new Spring Profile could be created or that one passed
from as VM arguments.

Make an S3 bucket:

```shell
aws s3api create-bucket \
    --bucket insurtechstorage \
    --region eu-north-1 \
    --create-bucket-configuration LocationConstraint=eu-north-1
```

## Build and run
An application could be run from both an IDE from the JAR archive or in a Docker container.

### An option from IDE
Fro that purpose add the following VM options into the application run settings:

```text
-DACCESS_KEY=your_access_key
-DSECRET_KEY=your_secret
```

Where your_access_key and your_secret would be your access and secret keys.

### As a standalone JAR

Execute the following commands:
```shell
mvn clean package
java -DACCESS_KEY=${ACCESS_KEY} -DSECRET_KEY=${SECRET_KEY} -jar ./document-service/target/document-service-1.0-SNAPSHOT.jar
```

Where your_access_key and your_secret would be your access and secret keys.

### As a Docker container

Execute the following commands:
```shell
mvn clean package
cd ./document-service
docker build . -t document-service
docker run -e ACCESS_KEY=your_access_key -e SECRET_KEY=your_secret -p 8080:8080 document-service
```

Where your_access_key and your_secret would be your access and secret keys.
