package red.tetracube.api.dto.device;

import red.tetracube.models.enumerations.DeviceInteractionClass;
import red.tetracube.models.enumerations.DeviceInteractionType;

public record DeviceInteraction(
        DeviceInteractionType interactionType,
        DeviceInteractionClass interactionClass) {

}
