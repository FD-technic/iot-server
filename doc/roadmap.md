# IoT Server Roadmap

This document outlines the planned development of the IoT Server backend.

The roadmap evolves together with the project as new features and integrations are implemented.

---

## Quick Links

📦 **IoT Server Repository**

https://github.com/FD-technic/iot-server

📦 **IoT Devices Repository**

https://github.com/FD-technic/iot-devices

---

# Current Status

## Completed

- Spring Boot REST API
- Device communication protocol
- JSON serialization
- Command architecture
- Health endpoint
- Linux deployment

The server provides a reusable communication platform for ESP32 devices.

---

# Short-Term Goals

## Device Registration

Support automatic registration of new devices.

Planned features

- unique device identification
- firmware version tracking
- device metadata

---

## PostgreSQL Integration

Store

- device information
- telemetry history
- command history

---

# Mid-Term Goals

## Configuration Management

Allow centralized device configuration.

Examples

- polling interval
- enabled sensors
- device parameters

---

## React Dashboard

Provide a web interface for

- connected devices
- current telemetry
- command management
- device status

---

# Long-Term Goals

## OTA Firmware Updates

Support remote firmware deployment.

---

## MQTT Support

Optional MQTT transport alongside REST communication.

---

## Advanced Monitoring

Future functionality

- alerts
- notifications
- historical charts
- device statistics

---

# Long-Term Vision

IoT Server aims to become a modular backend platform for IoT projects based on ESP32 devices.

The project also serves as a portfolio application demonstrating:

- Java
- Spring Boot
- REST API design
- IoT communication
- Linux deployment
- Clean architecture
- Scalable backend design

---

Developed by **Petr Hron**

🌐 https://fdweb.cz

💼 https://linkedin.com/in/petr-hron-dev