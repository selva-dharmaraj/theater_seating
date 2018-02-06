package edu.selva.batch;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class. Run the job with default input files when maven build runs.
 *
 * @author Selva Dharmaraj
 * @version 1.0, 2018-01-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchApplicationTests {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  private static String OS = System.getProperty("os.name").toLowerCase();

  static {
    String filePrefix = "file://";

    if (OS.indexOf("win") >= 0) {
      filePrefix = "file:///";
    }

    System.setProperty("layoutFile", filePrefix + new File("theater_layout_1.txt").getAbsolutePath());
    System.setProperty(
        "requestFile", filePrefix + new File("theater_seating_request_1.txt").getAbsolutePath());
  }

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Test
  public void testJob() {
    System.out.println("TEST method-------------------------------");
    try {
      JobStarter.execute("theaterSeatingJob");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (JobParametersInvalidException e) {
      e.printStackTrace();
    } catch (JobExecutionAlreadyRunningException e) {
      e.printStackTrace();
    } catch (JobRestartException e) {
      e.printStackTrace();
    } catch (JobInstanceAlreadyCompleteException e) {
      e.printStackTrace();
    }
  }
} // end class BatchApplicationTests
