package red.tetracube.kafka.dto.device.telemetry;

import java.time.Instant;

public record DeviceTelemetry(
        Instant telemetryTimestamp,
        String telemetryName,
        UnitsClass unitsClass,
        Units units,
        TelemetryInstanceValue value) {
}
