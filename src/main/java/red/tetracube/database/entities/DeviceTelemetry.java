package red.tetracube.database.entities;

import java.time.LocalDateTime;

import org.bson.BsonValue;
import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "devices_telemetry")
public class DeviceTelemetry extends PanacheMongoEntity {

    @BsonProperty("event_time")
    public LocalDateTime eventTime;
    
    @BsonProperty("event_meta")
    public EventMeta eventMeta;
    
    @BsonProperty("value")
    public BsonValue value;

}
