package com.ozangunalp;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.apicurio.registry.serde.avro.AvroKafkaDeserializer;
import io.apicurio.registry.serde.avro.AvroKafkaSerdeConfig;
import io.apicurio.registry.serde.avro.AvroKafkaSerializer;
import io.apicurio.registry.serde.avro.ReflectAvroDatumProvider;
import io.quarkus.avro.runtime.jackson.SpecificRecordBaseSerializer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
public class ClicksResourceTest {


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @InjectKafkaCompanion
    KafkaCompanion companion;

    @BeforeAll
    public static void beforeAll() {
        // Configure Rest mapper for Restassured
        OBJECT_MAPPER.registerModule(new SimpleModule().addSerializer(new SpecificRecordBaseSerializer()));
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        RestAssured.config = RestAssured.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> OBJECT_MAPPER));
    }

    @BeforeEach
    void setUp() {
        // Configure Avro Serde for PointerEvent
        companion.setCommonClientConfig(Map.of(AvroKafkaSerdeConfig.AVRO_DATUM_PROVIDER, ReflectAvroDatumProvider.class.getName()));
        Serde<PointerEvent> serde = Serdes.serdeFrom(new AvroKafkaSerializer<>(), new AvroKafkaDeserializer<>());
        serde.configure(companion.getCommonClientConfig(), false);
        companion.registerSerde(PointerEvent.class, serde);
    }

    @Test
    public void testPostClick() {
        companion.topics().delete("clicks");
        ConsumerTask<String, PointerEvent> clicks = companion.consume(PointerEvent.class)
                .fromTopics("clicks", 1);

        PointerEvent event = new PointerEvent("id", "user", "session", "mouse", "[0]", 1, 1, 1, 1, "desktop");
        given().body(event).contentType(ContentType.JSON)
                .when().post("/clicks")
                .then().statusCode(204);

        clicks.awaitCompletion();
    }

}