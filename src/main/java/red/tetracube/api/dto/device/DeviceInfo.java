package red.tetracube.api.dto.device;

import java.util.List;

import red.tetracube.models.enumerations.DeviceType;

public record DeviceInfo(
    String slug,
    String humanName,
    String roomSlug,
    DeviceType deviceType,
    List<DeviceInteraction> deviceCapabilities
) {
    
}
