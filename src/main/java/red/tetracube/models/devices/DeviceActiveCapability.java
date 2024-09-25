package red.tetracube.models.devices;

import red.tetracube.kafka.dto.device.telemetry.UnitsClass;
import red.tetracube.models.enumerations.DeviceCapability;

public record DeviceActiveCapability (
    DeviceCapability mode,
    UnitsClass unitsClass
) {

}