package domain.reminders.configs;

import domain.reminders.jobs.SendRemindersJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.time.Duration;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class JobFactoryBeanConfig {

    private final String MAIL_JOB_NAME = "Reminder_Message_Job";
    private final String MAIL_JOB_DESCRIPTION = "Invoke send Reminders Job service...";
    @Value("${quartz.sendMessageInterval.seconds}")
    private byte sendMessageIntervalSec;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(SendRemindersJob.class)
                .storeDurably()
                .withIdentity(MAIL_JOB_NAME)
                .withDescription(MAIL_JOB_DESCRIPTION)
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity(MAIL_JOB_NAME)
                .withDescription(MAIL_JOB_DESCRIPTION)
                .withSchedule(simpleSchedule()
                        .repeatForever()
                        .withIntervalInSeconds(sendMessageIntervalSec)
                )
                .build();
    }
}
