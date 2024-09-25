package red.tetracube.database.influx;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InfluDBClient {
    
    @ConfigProperty(name = "influxdb.url")
    String url;

    @ConfigProperty(name = "influxdb.username")
    String username;

    @ConfigProperty(name = "influxdb.password")
    String password;

    private final InfluxDBClient influxDB = InfluxDBClientFactory.create(url, username, password.toCharArray());

    public InfluxDBClient getInfluxDB() {
        return influxDB;
    }

}
