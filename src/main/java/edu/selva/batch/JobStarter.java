package edu.selva.batch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$, $Date$
 */
@ComponentScan
@EnableAutoConfiguration
public class JobStarter {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger(JobStarter.class);

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @throws InterruptedException DOCUMENT ME!
   * @throws JobParametersInvalidException DOCUMENT ME!
   * @throws JobExecutionAlreadyRunningException DOCUMENT ME!
   * @throws org.springframework.batch.core.repository.JobRestartException DOCUMENT ME!
   * @throws JobInstanceAlreadyCompleteException DOCUMENT ME!
   * @throws IllegalArgumentException DOCUMENT ME!
   */
  public static void execute(String jobName)
      throws InterruptedException, JobParametersInvalidException,
          JobExecutionAlreadyRunningException,
          org.springframework.batch.core.repository.JobRestartException,
          JobInstanceAlreadyCompleteException {
    SpringApplication app = new SpringApplication(JobStarter.class);
    app.setWebEnvironment(false);

    ConfigurableApplicationContext ctx = app.run(jobName);
    JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);

    Job addNewJob = ctx.getBean(jobName, Job.class);
    JobParameters jobParameters =
        new JobParametersBuilder().addDate("date", new Date()).toJobParameters();

    JobExecution jobExecution = jobLauncher.run(addNewJob, jobParameters);

    BatchStatus batchStatus = jobExecution.getStatus();

    while (batchStatus.isRunning()) {
      LOGGER.info("Still running...");
      Thread.sleep(1000);
    }

    ExitStatus exitStatus = jobExecution.getExitStatus();
    String exitCode = exitStatus.getExitCode();
    LOGGER.info(String.format("Exit status: %s", exitCode));

    // Based on exit code further action can be taken.
    JobInstance jobInstance = jobExecution.getJobInstance();
    LOGGER.info(
        String.format(
            "Job [%s][%s] finished running",
            jobInstance.getInstanceId(), jobInstance.getJobName()));

    //System.exit(0);
  } // end method main
} // end class SprtingBatchApplication
