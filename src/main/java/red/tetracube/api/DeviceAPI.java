package red.tetracube.api;

import java.util.List;

import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import red.tetracube.api.dto.device.DeviceInfo;
import red.tetracube.models.enumerations.DeviceType;
import red.tetracube.services.DeviceServices;

@Authenticated
@Path("/devices")
public class DeviceAPI {

    @Inject
    DeviceServices deviceServices;

    @GET
    @Path("/{deviceType}")
    @RunOnVirtualThread
    public List<DeviceInfo> getDevicesByType(@PathParam("deviceType") DeviceType deviceType) {
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
