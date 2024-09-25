package red.tetracube.kafka.handlers;

import java.io.IOException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import red.tetracube.database.entities.Device;
import red.tetracube.database.influx.InfluxDB;
import red.tetracube.database.influx.entities.BooleanState;
import red.tetracube.database.influx.entities.Current;
import red.tetracube.database.influx.entities.Frequency;
import red.tetracube.database.influx.entities.Power;
import red.tetracube.database.influx.entities.Quantity;
import red.tetracube.database.influx.entities.StringData;
import red.tetracube.database.influx.entities.SwitchState;
import red.tetracube.database.influx.entities.Temperature;
import red.tetracube.database.influx.entities.Time;
import red.tetracube.database.influx.entities.UPSState;
import red.tetracube.database.influx.entities.Voltage;
import red.tetracube.kafka.dto.device.telemetry.DeviceTelemetry;
import red.tetracube.kafka.dto.device.telemetry.TelemetryInstanceValue;
import red.tetracube.kafka.dto.device.telemetry.Units;
import red.tetracube.kafka.dto.device.telemetry.UnitsClass;

@ApplicationScoped
public class DeviceTelemetryHandler {

    @Inject
    InfluxDB influxDBClient;

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
        mapTelemetryAsEntity(deviceTelemetryRecord.key(), deviceTelemetryRecord.value());
        LOG.info("Saved telemetry {}", deviceTelemetryRecord.value().telemetryName());
    }

    @Transactional
    void mapTelemetryAsEntity(String deviceName, DeviceTelemetry message) throws IOException {
        var device = Device.getByInternalName(deviceName);
        if (device == null) {
            LOG.error("Cannot find any device named {} where register incoming telemetry", deviceName);
            throw new IOException("No device found with name " + deviceName);
        }

        if (message.units().equals(Units.CELSIUS)) {
            var temperatureMeasurement = new Temperature();
            temperatureMeasurement.device = device.slug;
            temperatureMeasurement.label = message.telemetryName();
            temperatureMeasurement.eventTS = message.telemetryTimestamp();
            temperatureMeasurement.units = Temperature.Units.fromMessage(message.units());
            temperatureMeasurement.unitsClass = Temperature.UnitsClass.fromMessage(message.unitsClass());
            temperatureMeasurement.value = ((TelemetryInstanceValue.FloatValue) message.value()).floatValue();
            influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), temperatureMeasurement);
        } else if (message.units().equals(Units.AMPERE)) {
            var currentMeasurement = new Current();
            currentMeasurement.device = device.slug;
            currentMeasurement.label = message.telemetryName();
            currentMeasurement.eventTS = message.telemetryTimestamp();
            currentMeasurement.units = Current.Units.fromMessage(message.units());
            currentMeasurement.unitsClass = Current.UnitsClass.fromMessage(message.unitsClass());
            currentMeasurement.value = ((TelemetryInstanceValue.FloatValue) message.value()).floatValue();
            influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), currentMeasurement);
        } else if (message.units().equals(Units.HERTZ)) {
            var frequencyMeasurement = new Frequency();
            frequencyMeasurement.device = device.slug;
            frequencyMeasurement.label = message.telemetryName();
            frequencyMeasurement.eventTS = message.telemetryTimestamp();
            frequencyMeasurement.units = Frequency.Units.fromMessage(message.units());
            frequencyMeasurement.unitsClass = Frequency.UnitsClass.fromMessage(message.unitsClass());
            frequencyMeasurement.value = ((TelemetryInstanceValue.FloatValue) message.value()).floatValue();
            influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), frequencyMeasurement);
        } else if (message.units().equals(Units.SECONDS)) {
            var timeMeasurement = new Time();
            timeMeasurement.device = device.slug;
            timeMeasurement.label = message.telemetryName();
            timeMeasurement.eventTS = message.telemetryTimestamp();
            timeMeasurement.units = Time.Units.fromMessage(message.units());
            timeMeasurement.unitsClass = Time.UnitsClass.fromMessage(message.unitsClass());
            timeMeasurement.value = ((TelemetryInstanceValue.LongValue) message.value()).longValue();
            influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), timeMeasurement);
        } else if (message.units().equals(Units.UNIT) || message.units().equals(Units.PERCENT) || message.units().equals(Units.RANGE)) {
            var quantityMeasurement = new Quantity();
            quantityMeasurement.device = device.slug;
            quantityMeasurement.label = message.telemetryName();
            quantityMeasurement.eventTS = message.telemetryTimestamp();
            quantityMeasurement.units = Quantity.Units.fromMessage(message.units());
            quantityMeasurement.unitsClass = Quantity.UnitsClass.fromMessage(message.unitsClass());
            if (message.value() instanceof TelemetryInstanceValue.LongValue) {
                quantityMeasurement.integerValue = ((TelemetryInstanceValue.LongValue) message.value()).longValue();
            } else if (message.value() instanceof TelemetryInstanceValue.FloatValue) {
                quantityMeasurement.floatValue = ((TelemetryInstanceValue.FloatValue) message.value()).floatValue();
            }
            influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), quantityMeasurement);
        } else if (message.units().equals(Units.VOLT)) {
            var voltageMeasurement = new Voltage();
            voltageMeasurement.device = device.slug;
            voltageMeasurement.label = message.telemetryName();
            voltageMeasurement.eventTS = message.telemetryTimestamp();
            voltageMeasurement.units = Voltage.Units.fromMessage(message.units());
            voltageMeasurement.unitsClass = Voltage.UnitsClass.fromMessage(message.unitsClass());
            voltageMeasurement.value = ((TelemetryInstanceValue.FloatValue) message.value()).floatValue();
            influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), voltageMeasurement);
        } else if (message.units().equals(Units.VOLT_AMPERE)) {
            var powerMeasurement = new Power();
            powerMeasurement.device = device.slug;
            powerMeasurement.label = message.telemetryName();
            powerMeasurement.eventTS = message.telemetryTimestamp();
            powerMeasurement.units = Power.Units.fromMessage(message.units());
            powerMeasurement.unitsClass = Power.UnitsClass.fromMessage(message.unitsClass());
            powerMeasurement.value = ((TelemetryInstanceValue.FloatValue) message.value()).floatValue();
            influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), powerMeasurement);
        } else if (message.units().equals(Units.NONE)) {
            if (message.unitsClass().equals(UnitsClass.BOOLEAN_STATE)) {
                var booleanState = new BooleanState();
                booleanState.device = device.slug;
                booleanState.label = message.telemetryName();
                booleanState.eventTS = message.telemetryTimestamp();
                booleanState.value = ((TelemetryInstanceValue.BoolValue) message.value()).boolValue();
                influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), booleanState);
            } else if (message.unitsClass().equals(UnitsClass.STRING)) {
                var stringData = new StringData();
                stringData.device = device.slug;
                stringData.label = message.telemetryName();
                stringData.eventTS = message.telemetryTimestamp();
                stringData.value = ((TelemetryInstanceValue.StringValue) message.value()).stringValue();
                influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), stringData);
            } else if (message.unitsClass().equals(UnitsClass.SWITCH_STATE)) {
                var switchStatus = new SwitchState();
                switchStatus.device = device.slug;
                switchStatus.label = message.telemetryName();
                switchStatus.eventTS = message.telemetryTimestamp();
                switchStatus.value = SwitchState.SwitchValue
                        .fromMessage(((TelemetryInstanceValue.SwitchValue) message.value()).switchValue());
                influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), switchStatus);
            } else if (message.unitsClass().equals(UnitsClass.UPS_STATE)) {
                var upsState = new UPSState();
                upsState.device = device.slug;
                upsState.label = message.telemetryName();
                upsState.eventTS = message.telemetryTimestamp();
                upsState.value = UPSState.UPSStateValue
                        .fromMessage(((TelemetryInstanceValue.UpsValue) message.value()).upsValue());
                influxDBClient.writeMeasurementOnBucket(device.deviceType.name().toLowerCase(), upsState);
            }
        }
    }

}
