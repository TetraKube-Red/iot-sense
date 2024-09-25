package red.tetracube.database.influx;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.domain.WritePrecision;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InfluxDB {
    
    @ConfigProperty(name = "influxdb.org")
    String organization;

    @ConfigProperty(name = "influxdb.url")
    String url;

    @ConfigProperty(name = "influxdb.username")
    String username;

    @ConfigProperty(name = "influxdb.password")
    String password;

    private InfluxDBClient influxDB;

    @PostConstruct
    public void initConnection() {
        influxDB = InfluxDBClientFactory.create(url, username, password.toCharArray());
    }

    public InfluxDBClient getInfluxDB() {
        return influxDB;
    }

    public <T> void writeMeasurementOnBucket(String bucket, T measurement) {
        getInfluxDB().getWriteApiBlocking().writeMeasurement(bucket, organization, WritePrecision.S, measurement);
    }

}
