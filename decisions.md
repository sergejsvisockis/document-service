# Technical decisions

DynamoDb has been chosen as the datastore due to its limitless scalability and key-value nature and cloud-native
approach this application is supposed to follow.
For the development purposes the local instance has been run having the following service in the docker-compose defined:

```yaml
db:
  command: "-jar DynamoDBLocal.jar -sharedDb -dbPath ./data"
  image: "amazon/dynamodb-local:latest"
  container_name: dynamodb-local
  ports:
    - "8000:8000"
  volumes:
    - "./docker/dynamodb:/home/dynamodblocal/data"
  working_dir: /home/dynamodblocal
```

However, before that that is mandatory to configure an AWS credentials.

Just execute:
```cmd
aws configure
```

Also see [this resource](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html)

The table be defined with this script:

```dynamo
create table '{
  "TableDescription": {
    "AttributeDefinitions": [
      {
        "AttributeName": "documentId","AttributeType": "S"
      }
    ],"TableName": "DocumentMetadata",
    "KeySchema": [
      {
        "AttributeName": "documentId","KeyType": "HASH"
      }
    ],
    "ProvisionedThroughput": {
      "ReadCapacityUnits": 1,"WriteCapacityUnits": 1
    },
    "TableClassSummary": {
      "TableClass": "STANDARD"
    }
  }
}';
```
