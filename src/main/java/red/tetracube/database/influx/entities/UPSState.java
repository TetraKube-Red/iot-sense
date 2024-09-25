package red.tetracube.database.influx.entities;

import java.time.Instant;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

@Measurement(name = "ups_state")
public class UPSState {
    
    @Column(tag = true)
    public String device;

    @Column(tag = true)
    public UnitsClass unitsClass = UPSState.UnitsClass.UPS_STATE;

    @Column(tag = true)
    public String label;

    @Column(timestamp = true)
    public Instant eventTS;

    @Column()
    public UPSStateValue value;

    public enum UPSStateValue {
        ONLINE,
        ON_BATTERY,
        LOW_BATTERY,
        HIGH_BATTERY,
        REPLACE_BATTERY,
        BATTERY_CHARGING,
        BATTERY_DISTCHARGING,
        BYPASS,
        UPS_CALIBRATING,
        OFFLINE,
        UPS_OVERLOAD,
        INCOMING_TRIMING,
        INCOMING_BOOST,
        FORCED_SHUTDOWN;

        public static UPSState.UPSStateValue fromMessage(red.tetracube.kafka.dto.device.telemetry.UPSState state) {
            return switch(state) {
                case BATTERY_CHARGING -> UPSState.UPSStateValue.BATTERY_CHARGING;
                case BATTERY_DISTCHARGING -> UPSState.UPSStateValue.BATTERY_DISTCHARGING;
                case BYPASS -> UPSState.UPSStateValue.BYPASS;
                case FORCED_SHUTDOWN -> UPSState.UPSStateValue.FORCED_SHUTDOWN;
                case HIGH_BATTERY -> UPSState.UPSStateValue.HIGH_BATTERY;
                case INCOMING_BOOST -> UPSState.UPSStateValue.INCOMING_BOOST;
                case INCOMING_TRIMING -> UPSState.UPSStateValue.INCOMING_TRIMING;
                case LOW_BATTERY -> UPSState.UPSStateValue.LOW_BATTERY;
                case OFFLINE -> UPSState.UPSStateValue.OFFLINE;
                case ONLINE -> UPSState.UPSStateValue.ONLINE;
                case ON_BATTERY -> UPSState.UPSStateValue.ON_BATTERY;
                case REPLACE_BATTERY -> UPSState.UPSStateValue.REPLACE_BATTERY;
                case UPS_CALIBRATING -> UPSState.UPSStateValue.UPS_CALIBRATING;
                case UPS_OVERLOAD -> UPSState.UPSStateValue.UPS_OVERLOAD;
            };
        }
    }

    public enum UnitsClass {
        UPS_STATE;
    }

}
