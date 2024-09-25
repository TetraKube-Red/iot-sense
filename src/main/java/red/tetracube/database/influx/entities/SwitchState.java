package red.tetracube.database.influx.entities;

import java.time.Instant;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

@Measurement(name = "switch")
public class SwitchState {
    
    @Column(tag = true)
    public String device;

    @Column(tag = true)
    public UnitsClass unitsClass = SwitchState.UnitsClass.SWITCH_STATE;

    @Column(tag = true)
    public String label;

    @Column(timestamp = true)
    public Instant eventTS;

    @Column()
    public SwitchValue value;

    public enum SwitchValue {
        ON,
        OFF;

        public static SwitchState.SwitchValue fromMessage(red.tetracube.kafka.dto.device.telemetry.SwitchState state) {
            return switch(state) {
                case red.tetracube.kafka.dto.device.telemetry.SwitchState.ON -> SwitchValue.ON;
                case red.tetracube.kafka.dto.device.telemetry.SwitchState.OFF -> SwitchValue.OFF;
                default -> throw new IllegalArgumentException("Unexpected value: " + state);
            };
        }
    }

    public enum UnitsClass {
        SWITCH_STATE;
    }

}
