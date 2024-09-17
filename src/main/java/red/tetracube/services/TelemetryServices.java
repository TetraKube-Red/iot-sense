package red.tetracube.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import red.tetracube.database.entities.Device;
import red.tetracube.database.entities.DeviceTelemetry;

@ApplicationScoped
public class TelemetryServices {

    private final static Logger LOGGER = LoggerFactory.getLogger(TelemetryServices.class);

    public List<DeviceTelemetry> getLatestDeviceTelemetry(String deviceSlug) {
        var device = Device.<Device>find("slug", deviceSlug).firstResultOptional();
        if (device.isEmpty()) {
            LOGGER.warn("Cannot find any device called {} to retrieve it's telemetry", deviceSlug);
            return new ArrayList<>();
        }
        return DeviceTelemetry.<DeviceTelemetry>list("event_meta.deviceReferenceId", Sort.by("event_time", Direction.Descending), device.get().id);
    }

}
