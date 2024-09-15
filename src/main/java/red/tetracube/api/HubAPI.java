package red.tetracube.api;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import red.tetracube.api.dto.hub.HubInfo;
import red.tetracube.api.dto.room.Room;
import red.tetracube.services.HubServices;

@Authenticated
@Path("/hubs")
public class HubAPI {
    
    @Inject
    JsonWebToken jwt;

    @Inject
    HubServices hubServices;

    @Path("/info")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public HubInfo getInfo() {
        var slug = jwt.getIssuer();
        var hubEntity = hubServices.getHubBySlug(slug)
            .orElseThrow(() -> new NotFoundException("Not found any hub with slug " + slug));
        var rooms = hubEntity.rooms.stream()
            .map(r -> new Room(r.slug, r.name))
            .toList();
        var hubInfo = new HubInfo(hubEntity.slug, hubEntity.name, rooms);
        return hubInfo;  
    }

}
