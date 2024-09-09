package red.tetracube.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class IoTSenseApp {

    private static final Logger LOG = LoggerFactory.getLogger(IoTSenseApp.class);

    public static void main(String... args) {
        LOG.info("Launching IoT sense App");
        Quarkus.run(args);
    }

}
