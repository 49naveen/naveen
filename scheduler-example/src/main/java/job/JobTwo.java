package job;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("jobTwo")
public class JobTwo {
	private final Logger logger = LoggerFactory.getLogger(JobTwo.class);

	public void start() {
		logger.debug("Job two started");
		try {
			TimeUnit.SECONDS.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("Job two finished");
	}
}
