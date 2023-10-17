package com.ozangunalp;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.avro.runtime.jackson.SpecificRecordBaseSerializer;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@QuarkusTest
public class ClicksResourceTest {

    @BeforeEach
    void setUp() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(new SpecificRecordBaseSerializer()));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        RestAssured.config = RestAssured.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> mapper));
    }

    @Test
    public void testPostClick() {
        PointerEvent event = new PointerEvent("id", "user", "session", "mouse", "[0]", 1, 1, 1, 1, "desktop");
        given().body(event).contentType(ContentType.JSON)
                .when().post("/clicks")
                .then().statusCode(204);
    }

}