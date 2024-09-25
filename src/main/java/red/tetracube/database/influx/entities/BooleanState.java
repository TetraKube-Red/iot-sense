package red.tetracube.database.influx.entities;

import java.time.Instant;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

@Measurement(name = "boolean")
public class BooleanState {
    
    @Column(tag = true)
    public String device;

    @Column(tag = true)
    public UnitsClass unitsClass = BooleanState.UnitsClass.BOOLEAN_STATE;

    @Column(tag = true)
    public String label;

    @Column(timestamp = true)
    public Instant eventTS;

    @Column()
    public Boolean value;

    public enum UnitsClass {
        BOOLEAN_STATE;
    }

}
