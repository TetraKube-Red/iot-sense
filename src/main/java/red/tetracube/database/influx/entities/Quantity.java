package red.tetracube.database.influx.entities;

import java.time.Instant;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

@Measurement(name = "quantity")
public class Quantity {
    
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
    public Long integerValue;

    @Column()
    public Float floatValue;

    public enum Units {
        UNIT,
        PERCENT,
        RANGE,
        NONE;

        public static Quantity.Units fromMessage(red.tetracube.kafka.dto.device.telemetry.Units units) {
            return switch(units) {
                case red.tetracube.kafka.dto.device.telemetry.Units.RANGE -> Units.RANGE;
                case red.tetracube.kafka.dto.device.telemetry.Units.PERCENT -> Units.PERCENT;
                case red.tetracube.kafka.dto.device.telemetry.Units.UNIT -> Units.UNIT;
                case red.tetracube.kafka.dto.device.telemetry.Units.NONE -> Units.NONE;
                default -> throw new IllegalArgumentException("Unexpected value: " + units);
            };
        }
    }

    public enum UnitsClass {
        QUANTITY;

        public static Quantity.UnitsClass fromMessage(red.tetracube.kafka.dto.device.telemetry.UnitsClass unitsClass) {
            return switch(unitsClass) {
                case red.tetracube.kafka.dto.device.telemetry.UnitsClass.QUANTITY -> UnitsClass.QUANTITY;
                default -> throw new IllegalArgumentException("Unexpected value: " + unitsClass);
            };
        }
    }

}
