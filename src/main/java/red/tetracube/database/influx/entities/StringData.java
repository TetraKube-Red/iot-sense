package red.tetracube.database.influx.entities;

import java.time.Instant;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

@Measurement(name = "string_data")
public class StringData {
    
    @Column(tag = true)
    public String device;

    @Column(tag = true)
    public UnitsClass unitsClass;

    @Column(tag = true)
    public String label;

    @Column(timestamp = true)
    public Instant eventTS;

    @Column()
    public String value;

    public enum UnitsClass {
        STRING;

        public static StringData.UnitsClass fromMessage(red.tetracube.kafka.dto.device.telemetry.UnitsClass unitsClass) {
            return switch(unitsClass) {
                case red.tetracube.kafka.dto.device.telemetry.UnitsClass.STRING -> UnitsClass.STRING;
                default -> throw new IllegalArgumentException("Unexpected value: " + unitsClass);
            };
        }
    }

}
