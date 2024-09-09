package red.tetracube.kafka.serde;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.arc.Arc;
import red.tetracube.kafka.dto.device.telemetry.DeviceTelemetry;

public class DeviceTelemetryDeserializer implements Deserializer<DeviceTelemetry> {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceTelemetryDeserializer.class);

    @Override
    public DeviceTelemetry deserialize(String topic, byte[] data) {
        var objectMapper = Arc.container().instance(ObjectMapper.class).get();
        try {
            return objectMapper.readValue(data, DeviceTelemetry.class);
        } catch (IOException e) {
            LOG.error("Cannot deserialize device telemetry caused by error:", e);
            return null;
        }
    }
    
}
