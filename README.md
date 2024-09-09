# IoT Sense

## Overview

Welcome to the IoT Data Collector and Streamer project! This open-source application is designed to facilitate the collection, storage, and real-time streaming of IoT data. It connects seamlessly with a Kafka message broker to receive data, stores it efficiently in a MongoDB database, and streams the collected data to mobile applications in real time.

### Key Features

* Data Ingestion via Kafka: The application consumes IoT data streams from a Kafka broker, allowing it to handle high-throughput, real-time data from various IoT devices.
* Efficient Data Storage: Data is stored in a MongoDB database for persistence, ensuring that all collected information is safely stored for further analysis and retrieval.
* Real-Time Data Streaming: The application streams data to connected mobile applications using real-time communication protocols, ensuring up-to-date information for users and devices.
* Scalable Design: Built to handle high volumes of data and users, the application can easily scale to meet the growing needs of IoT environments.

## Architecture

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

1. Kafka Integration: This application subscribes to Kafka topics and consumes messages containing IoT data from various sources (sensors, devices, etc.).
2. MongoDB Storage: Collected data is structured and stored in MongoDB, providing both scalability and flexibility in data storage.
3. Mobile Application Streaming: Using WebSockets or other real-time protocols, the application delivers updates and notifications directly to connected mobile clients.

## Getting Started

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

### Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

### Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/iot-sense-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.

## License

This project is licensed under the MIT License – see the [LICENSE](https://github.com/TetraKube-Red/iot-sense?tab=License-1-ov-file) file for details.

## Contact

For more information or support, please reach out to the project maintainers at penguinet.it@gmail.com.