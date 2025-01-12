# Spring Boot Quartz Scheduler Example

Spring Boot project using Quartz Scheduler. It shows how to configure and run batch jobs.

## Features

- Quartz Scheduler integration with Spring Boot
- YAML-based job configuration
- Custom batch job implementation
- Conditional scheduler activation

## Project Structure

```
├── config
│   └── BatchConfig.java           # Configuration class for batch settings
├── job
│   └── BatchJob.java             # Implementation of the batch job
├── scheduler
│   ├── ApplicationRunner.java     # Runner to start the scheduler
│   ├── Scheduler.java            # Main scheduler implementation
│   └── SchedulerUtils.java       # Utility class for scheduler
└── resources
    ├── application.yml           # Main application configuration
    └── application-batch-config.yml  # Batch-specific configuration
```

## Configuration

### Application Configuration (application.yml)
```yaml
spring:
  application:
    name: batch
  profiles:
    active: local
    include:
    - batch-config
```

### Batch Configuration (application-batch-config.yml)
```yaml
batch:
  scheduler:
    enabled: true
    jobs:
      batch-job:
        enabled: true
        schedule: 0 0/1 * * * ? # Runs every minute
```

## Implementation Details

### BatchConfig
The `BatchConfig` class uses `@ConfigurationProperties` to map YAML configuration to Java objects:
```java
@Configuration
@ConfigurationProperties(prefix = "batch")
@Data
public class BatchConfig {
    private Scheduler scheduler;
    // ... nested configuration classes
}
```

### BatchJob
```java
@Component
public class BatchJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // Job implementation
    }
}
```

### Key Points
1. The scheduler is only activated when `batch.scheduler.enabled=true`
2. Jobs can be individually enabled/disabled through configuration
3. Cron expressions can be configured in the YAML file
4. JobDataMap is used to pass configuration between components

## Getting Started

1. Clone the repository
2. Configure application-batch-config.yml with your desired schedule
3. Run the application:
```bash
./gradlew bootRun
```

## Dependencies

Add these configurations to your `build.gradle`:
```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.4.1'
    id 'io.spring.dependency-management' version '1.1.7'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'
    implementation 'org.quartz-scheduler:quartz:2.3.2'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

## Common Issues

1. **Scheduler not starting:**
   - Check if `batch.scheduler.enabled` is set to `true`
   - Verify that the batch-config profile is included

2. **Jobs not executing:**
   - Ensure individual job is enabled in configuration
   - Verify cron expression syntax
   - Check if BatchConfig is properly injected into JobDataMap

## License

This project is licensed under the MIT License - see below for details:

```
MIT License

Copyright (c) 2024 [Your Name]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Contributing

Feel free to submit issues and enhancement requests!
