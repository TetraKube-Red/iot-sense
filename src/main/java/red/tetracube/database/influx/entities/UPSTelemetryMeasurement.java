package red.tetracube.database.influx.entities;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import red.tetracube.models.enumerations.Units;

@Measurement(name = "temperature")
public class UPSTelemetryMeasurement {
    
    @Column(tag = true)
    public String device;

    @Column()
    public Units units;

    @Column(tag = true)
    public String label;

}
