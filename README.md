# IoT Server

---

The goal of this project is to provide a lightweight, extensible backend for ESP32-based IoT devices communicating over HTTP. It serves as a reusable foundation for home automation, monitoring, and future IoT projects.

---

Spring Boot backend for communication with IoT devices such as ESP32.

The project is designed as a universal communication server running on an Orange Pi. Devices periodically send their current state, and the server responds with commands to execute.

## Features

- REST API for IoT devices
- Generic device communication protocol
- Extensible payload architecture
- Command-based responses
- Health endpoint
- Ready for deployment on Linux (Orange Pi)
- Designed for future integration with PostgreSQL and React dashboard

---

## Technology Stack

- Java 21
- Spring Boot 4
- Maven
- Jackson 3
- REST API

---

## Project Structure

```
src
 ├── controller
 ├── service
 ├── dto
 ├── command
 ├── enums
 └── config
```

---

## Communication Flow

```text
ESP32
   │
   │ JSON
   ▼
Spring Boot
   │
Business Logic
   │
List<Command>
   │
   ▼
ESP32 executes commands
```

---

## Device Message Example

```json
{
  "deviceId": "esp32-01",
  "deviceType": "IRRIGATION",
  "firmware": "v0.1",
  "payload": {
    "mainTemperature": 25.0,
    "inputTemperature": 15.0,
    "outputTemperature": 5.0
  }
}
```

---

## Response Example

```json
{
  "commands": [
    {
      "type": "LED",
      "enabled": true
    },
    {
      "type": "TEMP",
      "value": 25.0
    }
  ]
}
```

---

## Health Endpoint

```
GET /api/health
```

Example response:

```json
{
  "status": "UP",
  "version": "v0.0.1",
  "timestamp": "2026-07-19T13:23:21Z"
}
```

---

## Build

```bash
mvn clean package
```

Output:

```
target/iot-server-0.0.1-SNAPSHOT.jar
```

---

## Running

```bash
java -jar target/iot-server-0.0.1-SNAPSHOT.jar
```

---

## Deployment

The application is intended to run as a `systemd` service on an Orange Pi.

Typical deployment:

```bash
sudo systemctl stop iot-server
scp target/iot-server-0.0.1-SNAPSHOT.jar root@orangepi:/opt/iot-server/
sudo systemctl start iot-server
```

---

## Roadmap

### Current

- REST communication
- Device protocol
- Command architecture
- Health endpoint
- Orange Pi deployment

### Planned

- PostgreSQL storage
- Device registration
- Configuration management
- React dashboard
- Historical charts
- OTA firmware updates
- MQTT support (optional)

---

## License

MIT