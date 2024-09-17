package red.tetracube.api.dto.telemetry;

import java.time.LocalDateTime;
import java.util.List;

public record Telemetry(
        String deviceSlug,
        LocalDateTime eventTime,
        List<TelemetryValue> telemetryValues) {
}
