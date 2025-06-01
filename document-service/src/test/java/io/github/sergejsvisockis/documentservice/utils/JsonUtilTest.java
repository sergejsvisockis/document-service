package io.github.sergejsvisockis.documentservice.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    void shouldConvertObjectToJsonString() {
        // given
        TestObject testObject = new TestObject("test", 123);

        // when
        String result = JsonUtil.toJson(testObject);

        // then
        assertEquals("{\"name\":\"test\",\"value\":123}", result);
    }

    @Test
    void shouldConvertMapToJsonString() {
        // given
        Map<String, Object> map = new HashMap<>();
        map.put("name", "test");
        map.put("value", 123);

        // when
        String result = JsonUtil.toJson(map);

        // then
        assertEquals("{\"name\":\"test\",\"value\":123}", result);
    }

    @Test
    void shouldHandleNullValues() {
        // given
        TestObject testObject = new TestObject(null, 0);

        // when
        String result = JsonUtil.toJson(testObject);

        // then
        assertEquals("{\"name\":null,\"value\":0}", result);
    }

    @Test
    void shouldHandleNestedObjects() {
        // given
        NestedTestObject nestedObject = new NestedTestObject(
                new TestObject("inner", 456),
                "outer"
        );

        // when
        String result = JsonUtil.toJson(nestedObject);

        // then
        assertEquals("{\"inner\":{\"name\":\"inner\",\"value\":456},\"description\":\"outer\"}", result);
    }

    private record TestObject(String name, int value) {
    }

    private record NestedTestObject(TestObject inner, String description) {
    }
}
