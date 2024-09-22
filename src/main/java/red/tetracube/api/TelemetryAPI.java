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
import red.tetracube.api.dto.telemetry.TelemetryInstanceValue;
import red.tetracube.api.dto.telemetry.TelemetryInstanceValue.BoolValue;
import red.tetracube.api.dto.telemetry.TelemetryInstanceValue.FloatValue;
import red.tetracube.api.dto.telemetry.TelemetryInstanceValue.LongValue;
import red.tetracube.api.dto.telemetry.TelemetryInstanceValue.StringValue;
import red.tetracube.api.dto.telemetry.TelemetryInstanceValue.SwitchValue;
import red.tetracube.api.dto.telemetry.TelemetryInstanceValue.UpsValue;
import red.tetracube.api.dto.telemetry.TelemetryValue;
import red.tetracube.services.TelemetryServices;

@Path("/device/{slug}/telemetry")
public class TelemetryAPI {

    @Inject
    TelemetryServices telemetryServices;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Telemetry getLatestTelemetry(@RestPath String slug) {
        var telemetries = telemetryServices.getLatestDeviceTelemetry(slug);
        var telemetryTimestamp = telemetries.getFirst().eventTime;
            var telemetryValues = telemetries.stream()
                .<TelemetryValue>map(telemetry -> {
                    TelemetryInstanceValue instanceValue = null;
                    if (telemetry.boolValue != null) {
                        instanceValue = new BoolValue(telemetry.boolValue);
                    } else if (telemetry.stringValue != null) {
                        instanceValue = new StringValue(telemetry.stringValue);
                    } else if (telemetry.longValue != null) {
                        instanceValue = new LongValue(telemetry.longValue);
                    } else if (telemetry.floatValue != null) {
                        instanceValue = new FloatValue(telemetry.floatValue);
                    } else if (telemetry.switchValue != null) {
                        instanceValue = new SwitchValue(telemetry.switchValue);
                    } else if (telemetry.upsValue != null) {
                        instanceValue = new UpsValue(telemetry.upsValue);
                    }

                    return new TelemetryValue(
                            telemetry.telemetryName,
                            telemetry.unitsClass,
                            telemetry.units,
                            instanceValue);
                })
                .toList();

        var telemetry = new Telemetry(slug, telemetryTimestamp, telemetryValues);
        return telemetry;
    }

}
