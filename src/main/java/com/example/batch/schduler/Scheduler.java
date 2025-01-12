package com.example.batch.schduler;

import com.example.batch.config.BatchConfig;
import com.example.batch.job.BatchJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {
    private org.quartz.Scheduler scheduler;

    @Autowired
    private BatchJob batchJob;

    public void run(JobDataMap jobDataMap) throws SchedulerException {
        log.info("scheduler run start");

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();

        SchedulerUtils schedulerUtils = new SchedulerUtils();
        BatchConfig batchConfig = (BatchConfig) jobDataMap.get("BatchConfig");
        BatchConfig.Scheduler.Jobs jobs = batchConfig.getScheduler().getJobs();

        if(jobs.getBatchJob().isEnabled()){
            JobDetail jobDetail = schedulerUtils.createJobDetail(batchJob, jobDataMap, "batchJob");
            Trigger trigger = schedulerUtils.createCronTrigger(jobDetail, jobs.getBatchJob().getSchedule());
            scheduler.scheduleJob(jobDetail, trigger);
        }

        scheduler.start();;
        log.info("scheduler run end");
    }

    public void stop() throws SchedulerException {
        if(scheduler != null) {
            log.info("scheduler shutdown start");

            scheduler.shutdown();

            log.info("scheduler shutdown end");
        }
    }
}
