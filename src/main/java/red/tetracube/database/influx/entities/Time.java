package red.tetracube.database.influx.entities;

import java.time.Instant;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

@Measurement(name = "time")
public class Time {
    
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
    public Long value;

    public enum Units {
        SECONDS;

        public static Time.Units fromMessage(red.tetracube.kafka.dto.device.telemetry.Units units) {
            return switch(units) {
                case red.tetracube.kafka.dto.device.telemetry.Units.SECONDS -> Units.SECONDS;
                default -> throw new IllegalArgumentException("Unexpected value: " + units);
            };
        }
    }

    public enum UnitsClass {
        TIME;

        public static Time.UnitsClass fromMessage(red.tetracube.kafka.dto.device.telemetry.UnitsClass unitsClass) {
            return switch(unitsClass) {
                case red.tetracube.kafka.dto.device.telemetry.UnitsClass.TIME -> UnitsClass.TIME;
                default -> throw new IllegalArgumentException("Unexpected value: " + unitsClass);
            };
        }
    }

}
