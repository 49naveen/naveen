package job;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("jobOne")
public class JobOne {
	private final Logger logger = LoggerFactory.getLogger(JobOne.class);
	
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private JobDetail jobTwoDetail;
	
	public void start() {
		logger.debug("Job one started");
		String cronExpression = "0/30 * * * * ?";
		logger.debug("Got latest cron expression for job two: " + cronExpression);
		CronTrigger cronTrigger = TriggerBuilder.newTrigger()
				.forJob(jobTwoDetail)
				.withIdentity("cronTrigger")
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
		TriggerKey triggerKey = cronTrigger.getKey();
		try {
			scheduler.rescheduleJob(triggerKey, cronTrigger);
			logger.debug("Rescheduled job two for latest cron schedule: " + cronExpression);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
}
