package edu.selva.batch;

import java.io.File;

import org.junit.Test;

import org.junit.runner.RunWith;

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

    System.setProperty("layoutFile", filePrefix + new File("theater_layout.txt").getAbsolutePath());
    System.setProperty(
        "requestFile", filePrefix + new File("theater_seating_request.txt").getAbsolutePath());
  }

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Test
  public void testJob() {
    System.out.println("TEST method-------------------------------");
  }
} // end class BatchApplicationTests
