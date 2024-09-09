package red.tetracube.kafka.dto.device.telemetry;

import java.time.LocalDateTime;

import red.tetracube.enumerations.Units;
import red.tetracube.enumerations.UnitsClass;

public record DeviceTelemetry(
        LocalDateTime telemetryTimestamp,
        String telemetryName,
        UnitsClass unitsClass,
        Units units,
        TelemetryInstanceValue value) {
}
