package red.tetracube.database.entities;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import red.tetracube.models.enumerations.Units;
import red.tetracube.models.enumerations.UnitsClass;

public class EventMeta {

    @BsonProperty("telemetry_name")
    public String telemetryName;

    @BsonProperty("units_class")
    public UnitsClass unitsClass;

    @BsonProperty("units")
    public Units units;

    @BsonProperty("device_reference_id")
    public ObjectId deviceReferenceId;

}
