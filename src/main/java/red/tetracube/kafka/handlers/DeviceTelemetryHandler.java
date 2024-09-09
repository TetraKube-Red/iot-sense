package red.tetracube.kafka.handlers;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.BsonBoolean;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import red.tetracube.database.entities.Device;
import red.tetracube.database.entities.EventMeta;
import red.tetracube.kafka.dto.device.telemetry.DeviceTelemetry;
import red.tetracube.kafka.dto.device.telemetry.TelemetryInstanceValue;

@ApplicationScoped
@RunOnVirtualThread
public class DeviceTelemetryHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceTelemetryHandler.class);

    @Incoming("device-telemetry")
    public void handleDeviceTelemetry(ConsumerRecord<String, DeviceTelemetry> deviceTelemetryRecord)
            throws IOException {
        if (deviceTelemetryRecord.value() == null) {
            LOG.warn("The message is emtpy, maybe for a deserialization problem. Ignoring the message");
            return;
        }
        LOG.info("Recived device telemetry data from {}", deviceTelemetryRecord.key());
        var savedTelemetry = mapTelemetryAsEntity(deviceTelemetryRecord.key(), deviceTelemetryRecord.value());
        LOG.info("Saved telemetry {}", savedTelemetry.eventMeta.telemetryName);
    }

    private red.tetracube.database.entities.DeviceTelemetry mapTelemetryAsEntity(String deviceName,
            DeviceTelemetry message)
            throws IOException {
        var device = Device.getByInternalName(deviceName);
        if (device == null) {
            LOG.error("Cannot find any device named {} where register incoming telemetry", deviceName);
            throw new IOException("No device found with name " + deviceName);
        }

        var deviceTelemetryEntity = new red.tetracube.database.entities.DeviceTelemetry();
        deviceTelemetryEntity.eventTime = message.telemetryTimestamp();
        deviceTelemetryEntity.eventMeta = new EventMeta();
        deviceTelemetryEntity.eventMeta.deviceReferenceId = device.id;
        deviceTelemetryEntity.eventMeta.telemetryName = message.telemetryName();
        deviceTelemetryEntity.eventMeta.units = message.units();
        deviceTelemetryEntity.eventMeta.unitsClass = message.unitsClass();

        if (message.value() instanceof TelemetryInstanceValue.StringValue) {
            var stringValue = ((TelemetryInstanceValue.StringValue) message.value()).stringValue();
            deviceTelemetryEntity.value = new BsonString(stringValue);
        } else if (message.value() instanceof TelemetryInstanceValue.IntValue) {
            var intValue = ((TelemetryInstanceValue.IntValue) message.value()).intValue();
            deviceTelemetryEntity.value = new BsonInt32(intValue);
        } else if (message.value() instanceof TelemetryInstanceValue.LongValue) {
            var longValue = ((TelemetryInstanceValue.LongValue) message.value()).longValue();
            deviceTelemetryEntity.value = new BsonInt64(longValue);
        } else if (message.value() instanceof TelemetryInstanceValue.DoubleValue) {
            var doubleValue = ((TelemetryInstanceValue.DoubleValue) message.value()).doubleValue();
            deviceTelemetryEntity.value = new BsonDouble(doubleValue);
        } else if (message.value() instanceof TelemetryInstanceValue.FloatValue) {
            var floatValue = ((TelemetryInstanceValue.FloatValue) message.value()).floatValue();
            deviceTelemetryEntity.value = new BsonDouble(floatValue);
        } else if (message.value() instanceof TelemetryInstanceValue.SwitchValue) {
            var switchValue = ((TelemetryInstanceValue.SwitchValue) message.value()).switchValue();
            deviceTelemetryEntity.value = new BsonString(switchValue.name());
        } else if (message.value() instanceof TelemetryInstanceValue.BoolValue) {
            var boolValue = ((TelemetryInstanceValue.BoolValue) message.value()).boolValue();
            deviceTelemetryEntity.value = new BsonBoolean(boolValue);
        } else if (message.value() instanceof TelemetryInstanceValue.UpsValue) {
            var upsValue = ((TelemetryInstanceValue.UpsValue) message.value()).upsValue();
            deviceTelemetryEntity.value = new BsonString(upsValue.name());
        }

        deviceTelemetryEntity.persist();

        return deviceTelemetryEntity;
    }

}
