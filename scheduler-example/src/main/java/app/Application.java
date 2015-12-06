package app;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("quartz-context.xml");
		Scheduler scheduler = (Scheduler) context.getBean("scheduler");
		JobListener jobListener = new JobListener() {
			private final Logger logger = LoggerFactory.getLogger(JobListener.class);
			private void printJobDetails(JobExecutionContext arg0, String methodName) {
				String jobName = arg0.getJobDetail().getKey().getName();				
				String jobGroup = arg0.getJobDetail().getKey().getGroup();
				logger.debug(methodName + "; name, group: " + jobName + ", " + jobGroup);
			}
			
			@Override
			public void jobWasExecuted(JobExecutionContext arg0, JobExecutionException arg1) {
				printJobDetails(arg0, "jobWasExecuted");
			}
			
			@Override
			public void jobToBeExecuted(JobExecutionContext arg0) {
				printJobDetails(arg0, "jobToBeExecuted");
			}
			
			@Override
			public void jobExecutionVetoed(JobExecutionContext arg0) {
				printJobDetails(arg0, "jobExecutionVetoed");
			}
			
			@Override
			public String getName() {
				return "JobListener";
			}
		};
		
		try {
			scheduler.getListenerManager().addJobListener(jobListener);
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
