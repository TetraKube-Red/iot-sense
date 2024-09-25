package red.tetracube.api.dto.telemetry;

import red.tetracube.kafka.dto.device.telemetry.Units;
import red.tetracube.kafka.dto.device.telemetry.UnitsClass;

public record TelemetryValue(
    String telemetryName,
    UnitsClass unitsClass,
    Units units,
    TelemetryInstanceValue instanceValue
) {
    
}
