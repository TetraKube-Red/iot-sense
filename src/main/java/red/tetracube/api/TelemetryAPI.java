package red.tetracube.api;

import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import red.tetracube.api.dto.telemetry.Telemetry;
import red.tetracube.api.dto.telemetry.TelemetryValue;
import red.tetracube.services.TelemetryServices;

//@Path("/device/{slug}/telemetry")
public class TelemetryAPI {

    @Inject
    TelemetryServices telemetryServices;
  /*  
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Telemetry getLatestTelemetry(@RestPath String slug) {
        var telemetries = telemetryServices.getLatestDeviceTelemetry(slug);
        var telemetryTimestamp = telemetries.getFirst().eventTime;
        var telemetryValues = telemetries.stream()
            .map(telemetry -> {
                return new TelemetryValue(
                    telemetry.eventMeta.telemetryName, 
                    telemetry.eventMeta.unitsClass,
                    telemetry.eventMeta.units, 
                    null);
            })
            .toList();

        var telemetry = new Telemetry(slug, telemetryTimestamp, telemetryValues);
        return telemetry;
    }*/

}
