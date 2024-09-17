package red.tetracube.models.devices;

import red.tetracube.models.enumerations.DeviceCapability;
import red.tetracube.models.enumerations.UnitsClass;

public record DeviceActiveCapability (
    DeviceCapability mode,
    UnitsClass unitsClass
) {

}