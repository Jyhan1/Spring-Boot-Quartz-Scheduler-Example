package com.example.batch.schduler;

import com.example.batch.config.BatchConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(
        value = "batch.scheduler.enabled",
        havingValue = "true",
        matchIfMissing = false
)
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner, DisposableBean {

    private final Scheduler scheduler;
    private final BatchConfig batchConfig;

    @Override
    public void destroy() throws Exception {
        if(scheduler != null)
            scheduler.stop();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("BatchConfig", batchConfig);
        scheduler.run(jobDataMap);
    }
}
