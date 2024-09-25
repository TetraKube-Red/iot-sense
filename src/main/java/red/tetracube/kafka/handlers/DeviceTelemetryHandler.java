package red.tetracube.kafka.handlers;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import red.tetracube.database.entities.Device;
import red.tetracube.kafka.dto.device.telemetry.DeviceTelemetry;
import red.tetracube.kafka.dto.device.telemetry.TelemetryInstanceValue;

@ApplicationScoped
public class DeviceTelemetryHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceTelemetryHandler.class);

    @Incoming("device-telemetry")
    @RunOnVirtualThread
    public void handleDeviceTelemetry(ConsumerRecord<String, DeviceTelemetry> deviceTelemetryRecord)
            throws IOException {
        if (deviceTelemetryRecord.value() == null) {
            LOG.warn("The message is emtpy, maybe for a deserialization problem. Ignoring the message");
            return;
        }
        LOG.info("Received device telemetry data from {}", deviceTelemetryRecord.key());
        var savedTelemetry = mapTelemetryAsEntity(deviceTelemetryRecord.key(), deviceTelemetryRecord.value());
        LOG.info("Saved telemetry {}", savedTelemetry.telemetryName);
    }

    @Transactional
    red.tetracube.database.entities.DeviceTelemetry mapTelemetryAsEntity(String deviceName,
            DeviceTelemetry message)
            throws IOException {
        var device = Device.getByInternalName(deviceName);
        if (device == null) {
            LOG.error("Cannot find any device named {} where register incoming telemetry", deviceName);     
            throw new IOException("No device found with name " + deviceName);
        }

        var deviceTelemetryEntity = new red.tetracube.database.entities.DeviceTelemetry();
        deviceTelemetryEntity.id = UUID.randomUUID();
        deviceTelemetryEntity.eventTime = message.telemetryTimestamp();
        deviceTelemetryEntity.device = device;
        deviceTelemetryEntity.telemetryName = message.telemetryName();
        deviceTelemetryEntity.units = message.units();
        deviceTelemetryEntity.unitsClass = message.unitsClass();

        if (message.value() instanceof TelemetryInstanceValue.StringValue) {
            var stringValue = ((TelemetryInstanceValue.StringValue) message.value()).stringValue();
            deviceTelemetryEntity.stringValue = stringValue;
        } else if (message.value() instanceof TelemetryInstanceValue.IntValue) {
            var intValue = ((TelemetryInstanceValue.IntValue) message.value()).intValue();
            deviceTelemetryEntity.intValue = intValue;
        } else if (message.value() instanceof TelemetryInstanceValue.LongValue) {
            var longValue = ((TelemetryInstanceValue.LongValue) message.value()).longValue();
            deviceTelemetryEntity.longValue = longValue;
        } else if (message.value() instanceof TelemetryInstanceValue.DoubleValue) {
            var doubleValue = ((TelemetryInstanceValue.DoubleValue) message.value()).doubleValue();
            deviceTelemetryEntity.doubleValue = doubleValue;
        } else if (message.value() instanceof TelemetryInstanceValue.FloatValue) {
            var floatValue = ((TelemetryInstanceValue.FloatValue) message.value()).floatValue();
            deviceTelemetryEntity.floatValue = floatValue;
        } else if (message.value() instanceof TelemetryInstanceValue.SwitchValue) {
            var switchValue = ((TelemetryInstanceValue.SwitchValue) message.value()).switchValue();
            deviceTelemetryEntity.switchValue = switchValue;
        } else if (message.value() instanceof TelemetryInstanceValue.BoolValue) {
            var boolValue = ((TelemetryInstanceValue.BoolValue) message.value()).boolValue();
            deviceTelemetryEntity.boolValue = boolValue;
        } else if (message.value() instanceof TelemetryInstanceValue.UpsValue) {
            var upsValue = ((TelemetryInstanceValue.UpsValue) message.value()).upsValue();
            deviceTelemetryEntity.upsValue = upsValue;
        }

        deviceTelemetryEntity.persist();

        return deviceTelemetryEntity;
    }

}
