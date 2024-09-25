package red.tetracube.database.influx.entities;

import java.time.Instant;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

@Measurement(name = "voltage")
public class Voltage {
    
    @Column(tag = true)
    public String device;

    @Column(tag = true)
    public Units units;

    @Column(tag = true)
    public UnitsClass unitsClass;

    @Column(tag = true)
    public String label;

    @Column(timestamp = true)
    public Instant eventTS;

    @Column()
    public Float value;

    public enum Units {
        VOLT;

        public static Voltage.Units fromMessage(red.tetracube.kafka.dto.device.telemetry.Units units) {
            return switch(units) {
                case red.tetracube.kafka.dto.device.telemetry.Units.VOLT -> Units.VOLT;
                default -> throw new IllegalArgumentException("Unexpected value: " + units);
            };
        }
    }

    public enum UnitsClass {
        VOLTAGE;

        public static Voltage.UnitsClass fromMessage(red.tetracube.kafka.dto.device.telemetry.UnitsClass unitsClass) {
            return switch(unitsClass) {
                case red.tetracube.kafka.dto.device.telemetry.UnitsClass.VOLTAGE -> UnitsClass.VOLTAGE;
                default -> throw new IllegalArgumentException("Unexpected value: " + unitsClass);
            };
        }
    }

}
