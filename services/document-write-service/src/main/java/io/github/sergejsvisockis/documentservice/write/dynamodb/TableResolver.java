package io.github.sergejsvisockis.documentservice.write.dynamodb;

import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import org.springframework.stereotype.Component;

@Component
public class TableResolver implements DynamoDbTableNameResolver {

    @Override
    public <T> String resolve(Class<T> clazz) {
        String tableName = clazz.getAnnotation(TableName.class).name();
        return !tableName.isEmpty()
                ? tableName
                : clazz.getSimpleName();
    }
}
