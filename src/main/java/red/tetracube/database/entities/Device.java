package red.tetracube.database.entities;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import red.tetracube.models.devices.DeviceActiveCapability;
import red.tetracube.models.enumerations.DeviceType;

@MongoEntity(collection = "devices")
public class Device extends PanacheMongoEntity{

    @BsonProperty("internal_name")
    public String internalName;

    @BsonProperty("slug")
    public String slug;

    @BsonProperty("human_name")
    public String humanName;

    @BsonProperty("room_slug")
    public String roomSlug;
    
    @BsonProperty("device_type")
    public DeviceType deviceType;

    @BsonProperty("device_capabilities")
    public List<DeviceActiveCapability> deviceCapabilities;

    public static Device getByInternalName(String internalName) {
        return Device.<Device>find("internal_name", internalName)
                .firstResult();
    }

}
