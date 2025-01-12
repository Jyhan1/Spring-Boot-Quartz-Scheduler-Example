package com.example.batch.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "batch")
@Slf4j
@Data
public class BatchConfig {
    private Scheduler scheduler;

    @Data
    public static class Scheduler {
        private boolean enabled;
        private Jobs jobs;

        @Data
        public static class Jobs {
            private BatchJob batchJob;

            public static class BatchJob extends JobInfo { }

            @Data
            public static class JobInfo {
                private boolean enabled;
                private String schedule;
            }
        }
    }
}
