package red.tetracube.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import red.tetracube.database.entities.Device;
import red.tetracube.models.enumerations.DeviceType;

@ApplicationScoped
public class DeviceServices {

    public List<Device> getDevicesByType(DeviceType deviceType) {
        return Device.find("device_type", deviceType)
                .list();
    }

}
