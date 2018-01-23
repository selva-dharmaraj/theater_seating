package edu.selva.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import org.springframework.stereotype.Component;

/**
 * This listener class triggered when the given job completes.
 *
 * @author Selva Dharmaraj
 * @version 1.0, 2018-01-22
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  private static final Logger log =
      LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * @see
   *     org.springframework.batch.core.listener.JobExecutionListenerSupport#afterJob(org.springframework.batch.core.JobExecution)
   */
  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");
    }
  }
}
