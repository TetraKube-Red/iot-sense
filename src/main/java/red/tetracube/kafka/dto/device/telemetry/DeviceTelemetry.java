package red.tetracube.kafka.dto.device.telemetry;

import java.time.LocalDateTime;

import red.tetracube.models.enumerations.Units;
import red.tetracube.models.enumerations.UnitsClass;

public record DeviceTelemetry(
        LocalDateTime telemetryTimestamp,
        String telemetryName,
        UnitsClass unitsClass,
        Units units,
        TelemetryInstanceValue value) {
}
