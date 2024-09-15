package red.tetracube.api.dto.hub;

import java.util.List;

import red.tetracube.api.dto.room.Room;

public record HubInfo(
    String slug,
    String name,
    List<Room> rooms
) {
    
}
