package red.tetracube.api;

import java.util.List;

import org.jboss.resteasy.reactive.RestPath;

import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import red.tetracube.api.dto.device.DeviceInfo;
import red.tetracube.api.dto.device.DeviceInteraction;
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
                .<DeviceInfo>map(device -> {
                    var interactions = device.deviceInteractions.stream()
                    .map(interaction -> {
                        return new DeviceInteraction(interaction.interactionType, interaction.interactionClass);
                    })
                    .toList();
                    var roomSlug = device.roomSlug;
                    return new DeviceInfo(
                            device.slug,
                            device.humanName,
                            roomSlug,
                            device.deviceType,
                            interactions);
                })
                .toList();
    }

}
