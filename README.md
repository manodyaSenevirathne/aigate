# AI-Gate++: Pluggable API Gateway with AI-Powered Security

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![License](https://img.shields.io/badge/license-MIT-blue)
![Java](https://img.shields.io/badge/java-17%2B-orange)
![Python](https://img.shields.io/badge/python-3.8%2B-yellow)
![Issues](https://img.shields.io/github/issues/your-username/aigate-project)
![Last Commit](https://img.shields.io/github/last-commit/your-username/aigate-project)


AI-Gate++ is a modern, extensible API Gateway designed for high-performance, pluggable traffic management and AI-driven security. It features a Spring Boot-based core, a plugin system (supporting both in-process and external plugins), and an integrated anomaly detection engine using an autoencoder trained on the KDD99 dataset.

---

## Project Structure

```
ai-engine/           # Python-based AI engine for anomaly detection (KDD99 autoencoder, preprocessing)
gateway-core/        # Java/Spring Boot API Gateway core (routing, plugins, config, TCP/HTTP support)
infra/               # Infrastructure as code, deployment scripts (optional)
plugin-sandbox/      # Example plugins and test harnesses
tcp-agent/           # TCP agent for plugin communication (optional)
README.md            # This file
```

---

## Features

- **Dynamic Routing:** HTTP and TCP routing based on YAML configuration.
- **Plugin System:** Core plugins (Java, @Component) and external plugins (register via REST, communicate via TCP).
- **Centralized Configuration:** Grouped config loading, hot updates via REST.
- **Rate Limiting:** Configurable, per-client rate limiting.
- **AI Security:** Autoencoder-based anomaly detection (KDD99), pluggable into request pipeline.
- **Monitoring:** Logging plugin, extensible for metrics.
- **Extensible:** Add new plugins (Java or external), new protocols, or AI models.

---

## Quick Start

### 1. Build and Run the Gateway

```sh
cd gateway-core
./gradlew build
java -jar build/libs/app.jar
```

Or use Docker:

```sh
docker build -t aigate-core .
docker run -p 8080:8080 aigate-core
```

### 2. Train and Export the AI Model

The AI engine is in `ai-engine/Autoencoder_KDD99.ipynb`:

- Open the notebook and run all cells.
- This will produce:
  - `api_gateway_autoencoder.h5` (Keras model)
  - `feature_scaler.pkl` (scaler)
  - `label_encoders.pkl` (for categorical features)
  - `model_config.pkl` (feature list, threshold, etc.)

Copy these files to `ai-engine/` or a location accessible by the gateway.

### 3. Configure Routes

Edit `gateway-core/src/main/resources/routes.yml`:

```yaml
routes:
  - id: example-http
    pathPattern: /api/.*
    protocol: HTTP
    host: backend-service
    port: 9000
  - id: example-tcp
    pathPattern: /tcp/.*
    protocol: TCP
    host: tcp-plugin-host
    port: 7070
```

### 4. Central Configuration

Configure rate limiting and other settings in `application.yml`:

```yaml
aigate-config:
  rate-limiter.window-size-in-millis: "60000"
  rate-limiter.max-requests: "100"
```

---

## REST API

### Gateway Endpoints

- `GET /hello` — Health check.
- `GET /routes` — List all loaded routes.
- `POST /config/{name}` — Update config group `{name}`.
- `GET /config/{name}` — Get config group `{name}`.

### Plugin Management

- `GET /plugins` — List all registered plugins.
- `POST /plugins/register` — Register an external plugin (send `ExternalPluginMetadata`).

#### Example `ExternalPluginMetadata` JSON

```json
{
  "name": "MyExternalPlugin",
  "host": "127.0.0.1",
  "port": 7070,
  "stages": ["request", "response"]
}
```

---

## Plugin System

### Core Plugins

- Implement `com.aigate.aigate_core.interfaces.CorePlugin`
- Annotate with `@Component`
- Registered automatically at startup

### External Plugins

- Register via `/plugins/register`
- Communicate via TCP using a binary protocol (see `BinaryMessageUtil`)
- Example server: `TcpMockPluginServer`

---

## AI Anomaly Detection

- Model: Autoencoder trained on KDD99 features relevant to API traffic
- Preprocessing: StandardScaler, LabelEncoders
- Prediction: See `predict_anomaly` in `ai-engine/Autoencoder_KDD99.ipynb`
- Deployment: Load model/scaler/encoders in the gateway (integration code required)

---

## Development

### Java (Gateway Core)

- Java 17+, Spring Boot 3.x
- Build: `./gradlew build`
- Test: `./gradlew test`

### Python (AI Engine)

- Python 3.8+
- Install dependencies: `pip install -r requirements.txt` (see notebook for details)

---

## Example: Registering and Using an External Plugin

1. Start the mock plugin server:

   ```sh
   cd gateway-core
   java -cp build/libs/app.jar com.aigate.aigate_core.mpTalk.TcpMockPluginServer
   ```

2. Register the plugin:

   ```sh
   curl -X POST http://localhost:8080/plugins/register \
     -H "Content-Type: application/json" \
     -d '{"name":"TestPlugin","host":"127.0.0.1","port":7070,"stages":["request"]}'
   ```

3. Send traffic matching the plugin's route.

---

## References

- [KDD99 Dataset](http://kdd.ics.uci.edu/databases/kddcup99/kddcup99.html)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [TensorFlow Keras](https://www.tensorflow.org/guide/keras)

---

## License

MIT License. See [LICENSE](LICENSE) for details.

