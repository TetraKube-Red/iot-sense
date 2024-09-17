package red.tetracube.api.dto.telemetry;

import red.tetracube.models.enumerations.Units;
import red.tetracube.models.enumerations.UnitsClass;

public record TelemetryValue(
    String telemetryName,
    UnitsClass unitsClass,
    Units units,
    TelemetryInstanceValue instanceValue
) {
    
}
