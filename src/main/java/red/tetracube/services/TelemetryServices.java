package red.tetracube.services;

import java.util.HashMap;
import java.util.List;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import red.tetracube.database.entities.DeviceTelemetry;

@ApplicationScoped
public class TelemetryServices {

    public List<DeviceTelemetry> getLatestDeviceTelemetry(String deviceSlug) {
        var latestEventTime = DeviceTelemetry.<DeviceTelemetry>find(
                "device.slug",
                Sort.by("eventTime", Direction.Descending),
                deviceSlug)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException())
                .eventTime;
        var params = new HashMap<String, Object>() {
            {
                put("slug", deviceSlug);
                put("eventTime", latestEventTime);
            }
        };
        return DeviceTelemetry.<DeviceTelemetry>find(
                "device.slug = :slug and eventTime = :eventTime",
                Sort.by("eventTime", Direction.Descending),
                params)
                .list();
    }

}
