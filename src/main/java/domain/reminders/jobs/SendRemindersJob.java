package domain.reminders.jobs;

import domain.reminders.services.ReminderWorkerService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendRemindersJob implements Job {

    private final ReminderWorkerService jobService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        jobService.sendReminders();
    }
}