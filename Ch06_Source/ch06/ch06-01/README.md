# AMQP Hello Project

This project demonstrates a simple Spring RabbitMQ AMQP example for sending and receiving messages. The build process is managed using Maven.

## Features of the `pom.xml`

The `pom.xml` file has been enhanced to replicate the functionality of the Ant build script. Below are the key features:

### 1. **Dependency Management**
The `pom.xml` file includes all the required dependencies for the project:
- **RabbitMQ Client**: `amqp-client` for interacting with RabbitMQ.
- **Logging**: SLF4J and Log4j2 for logging functionality.

Dependencies are automatically downloaded and managed by Maven, eliminating the need for manual classpath configuration.

### 2. **Build Lifecycle**
The Maven build lifecycle handles the following tasks:
- **Clean**: Deletes the `target` directory (`mvn clean`).
- **Compile**: Compiles the Java source files (`mvn compile`).

### 3. **Profiles for Running Classes**
Two Maven profiles have been added for the `listen` and `send` targets from the Ant script:
- **`listen` Profile**: Runs the `com.acme.ch06.ex01.Receive` class.
- **`send` Profile**: Runs the `com.acme.ch06.ex01.Send` class.

#### How to Use Profiles:
- To run the `Receive` class:
  ```bash
  mvn test -Plisten
  ```
- To run the `Send` class:
  ```bash
  mvn test -Psend
  ```

### 4. **Plugins**

a. Maven Compiler Plugin
The `maven-compiler-plugin` is configured to compile the project using Java 17:

```xml
<source>17</source>
<target>17</target>
```

b. Exec Maven Plugin
The `exec-maven-plugin` is used to execute the `Receive` and `Send` classes. It is configured within the profiles to run the respective classes.

## How to Build and Run the Project

### Build the Project

To clean and compile the project, run:
```bash
mvn clean compile
```

### Run the `Receive` Class
To run the `Receive` class, use the `listen` profile:
```bash
mvn test -Plisten
```

### Run the `Send` Class
To run the `Send` class, use the `send` profile:
```bash
mvn test -Psend
```

## Project Structure
```
src/
├── main/
│   ├── java/               # Java source files
│   └── resources/          # Resource files
└── test/
    ├── java/               # Test source files
    └── resources/          # Test resource files
```

## Dependencies
The following dependencies are included in the `pom.xml`:
- **RabbitMQ Client**: `com.rabbitmq:amqp-client:5.25.0`
- **SLF4J API**: `org.slf4j:slf4j-api:2.0.9`
- **Log4j2**:
  - `org.apache.logging.log4j:log4j-api:2.20.0`
  - `org.apache.logging.log4j:log4j-core:2.20.0`
  - `org.apache.logging.log4j:log4j-slf4j-impl:2.20.0`

## Notes
- Ensure RabbitMQ is running and properly configured before running the `Receive` and `Send` classes.
- The project uses Java 17. Make sure your environment is set up accordingly.

## License
This project is licensed under the MIT License.

