package red.tetracube.api;

import java.util.List;

import org.jboss.resteasy.reactive.RestPath;

import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import red.tetracube.api.dto.device.DeviceInfo;
import red.tetracube.models.enumerations.DeviceType;
import red.tetracube.services.DeviceServices;

@Authenticated
@Path("/devices")
public class DeviceAPI {

    @Inject
    DeviceServices deviceServices;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{deviceType}")
    @RunOnVirtualThread
    public List<DeviceInfo> getDevicesByType(@RestPath DeviceType deviceType) {
        return deviceServices.getDevicesByType(deviceType).stream()
                .map(device -> {
                    return new DeviceInfo(
                            device.slug,
                            device.humanName,
                            device.roomSlug,
                            device.deviceType,
                            device.deviceCapabilities);
                })
                .toList();
    }

}
