package io.github.sergejsvisockis.documentservice.write.repository;

import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import org.springframework.stereotype.Component;

@Component
public class TableNameResolver implements DynamoDbTableNameResolver {

    @Override
    public <T> String resolve(Class<T> clazz) {
        return clazz.getAnnotation(TableName.class).name();
    }
}
