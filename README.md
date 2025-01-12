# Spring Boot Quartz Scheduler Example

This project demonstrates how to implement a Quartz Scheduler in a Spring Boot application with a simple batch job configuration.

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
./mvnw spring-boot:run
```

## Dependencies

Add these dependencies to your `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.quartz-scheduler</groupId>
        <artifactId>quartz</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

## Common Issues

1. **Scheduler not starting:**
   - Check if `batch.scheduler.enabled` is set to `true`
   - Verify that the batch-config profile is included

2. **Jobs not executing:**
   - Ensure individual job is enabled in configuration
   - Verify cron expression syntax
   - Check if BatchConfig is properly injected into JobDataMap

## Contributing

Feel free to submit issues and enhancement requests!
