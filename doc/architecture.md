# IoT Server Architecture

## Project Overview

IoT Server is a lightweight Spring Boot backend that provides communication between IoT devices and server-side services.

Devices periodically send telemetry data over HTTP and receive commands to execute.

---

## Quick Links

📦 **IoT Server Repository**

https://github.com/FD-technic/iot-server

📦 **IoT Devices Repository**

https://github.com/FD-technic/iot-devices

---

# System Architecture

```text
      ESP32 Devices
             │
       JSON over HTTP
             ▼
     Spring Boot REST API
             │
      Business Logic
             │
      Command Processing
             │
        Device Commands
             ▼
      JSON HTTP Response
```

---

# Project Structure

```text
src
│
├── command
├── config
├── controller
├── dto
├── enums
└── service
```

---

# Application Layers

| Layer | Responsibility |
|--------|----------------|
| controller | REST endpoints |
| service | Business logic |
| dto | Communication objects |
| command | Device command model |
| enums | Shared enumerations |
| config | Application configuration |

---

# Communication Flow

1. ESP32 sends telemetry data.
2. Controller receives the request.
3. Service validates and processes the message.
4. Business logic generates device commands.
5. Commands are serialized into JSON.
6. ESP32 executes received commands.

---

# Design Principles

- Layered architecture
- RESTful communication
- Stateless requests
- JSON payloads
- Command-based protocol
- Extensible device model

---

Developed by **Petr Hron**

🌐 https://fdweb.cz

💼 https://linkedin.com/in/petr-hron-dev