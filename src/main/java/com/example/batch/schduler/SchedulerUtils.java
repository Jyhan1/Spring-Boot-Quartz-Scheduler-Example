package com.example.batch.schduler;

import org.quartz.*;

public class SchedulerUtils {

    public JobDetail createJobDetail(Job job, JobDataMap jobDataMap, String jobKey) {
        return JobBuilder.newJob(job.getClass())
                .withIdentity(jobKey)
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    public Trigger createCronTrigger(JobDetail jobDetail, String cronExp){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                .build();
    }
}
