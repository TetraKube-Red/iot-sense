package red.tetracube.kafka.dto.device.telemetry;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,  // Include the type info in the JSON
    include = JsonTypeInfo.As.PROPERTY  // Include as a wrapper object
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TelemetryInstanceValue.StringValue.class, name = "stringValue"),
    @JsonSubTypes.Type(value = TelemetryInstanceValue.IntValue.class, name = "intValue"),
    @JsonSubTypes.Type(value = TelemetryInstanceValue.LongValue.class, name = "longValue"),
    @JsonSubTypes.Type(value = TelemetryInstanceValue.DoubleValue.class, name = "doubleValue"),
    @JsonSubTypes.Type(value = TelemetryInstanceValue.FloatValue.class, name = "floatValue"),
    @JsonSubTypes.Type(value = TelemetryInstanceValue.SwitchValue.class, name = "switchValue"),
    @JsonSubTypes.Type(value = TelemetryInstanceValue.BoolValue.class, name = "boolValue"),
    @JsonSubTypes.Type(value = TelemetryInstanceValue.UpsValue.class, name = "upsValue")
})
public sealed interface TelemetryInstanceValue permits TelemetryInstanceValue.StringValue,
        TelemetryInstanceValue.IntValue,
        TelemetryInstanceValue.LongValue,
        TelemetryInstanceValue.FloatValue,
        TelemetryInstanceValue.BoolValue,
        TelemetryInstanceValue.DoubleValue,
        TelemetryInstanceValue.SwitchValue,
        TelemetryInstanceValue.UpsValue {

    public record StringValue(String stringValue) implements TelemetryInstanceValue {
    }

    public record IntValue(int intValue) implements TelemetryInstanceValue {
    }

    public record LongValue(long longValue) implements TelemetryInstanceValue {
    }

    public record DoubleValue(double doubleValue) implements TelemetryInstanceValue {
    }

    public record FloatValue(float floatValue) implements TelemetryInstanceValue {
    }

    public record SwitchValue(SwitchState switchValue) implements TelemetryInstanceValue {
    }

    public record BoolValue(boolean boolValue) implements TelemetryInstanceValue {
    }

    public record UpsValue(UPSState upsValue) implements TelemetryInstanceValue {
    }
}
