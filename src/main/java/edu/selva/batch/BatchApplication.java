package edu.selva.batch;

import java.io.File;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is SprintBootApplication class. This application takes two inputs as String argument. This
 * Class initialize require context and starts up <b>theaterSeatingJob</b> batch job for loading
 * input file data in to memory and seat arrangement task.
 *
 * @author Selva Dharmaraj
 * @see edu.selva.batch.configuration.TheaterSeatingJobConfiguration
 * @version 1.0, 2018-01-22
 */
@EnableBatchProcessing
@SpringBootApplication
public class BatchApplication {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  private static String OS = System.getProperty("os.name").toLowerCase();

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static boolean isWindows() {
    return (OS.indexOf("win") >= 0);
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param args DOCUMENT ME!
   * @throws Exception DOCUMENT ME!
   */
  public static void main(String[] args) throws Exception {
    if (args.length > 1) {
      String filePrefix = "file://";

      if (isWindows()) {
        filePrefix = "file:///";
      }

      try {
        System.setProperty("layoutFile", filePrefix + new File(args[0]).getAbsolutePath());
        System.setProperty("requestFile", filePrefix + new File(args[1]).getAbsolutePath());
      } catch (NumberFormatException e) {
        System.err.println("Error in parsing arguments. Please verify your input files location.");
        printUsageFomat();
        System.exit(1);
      }
    } else {
      printUsageFomat();
      System.exit(1);
    }

    // Run Spring batch job
    // SpringApplication.run(BatchApplication.class, args);
    JobStarter.execute("theaterSeatingJob");
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static void printUsageFomat() {
    System.err.println(
        "Argument Error:"
            + "\n Usage: java -jar <jar_file> <layout_file> <request_file>"
            + "\n Example: java -jar target/theater.seating-0.0.1-SNAPSHOT.jar /Users/selva/theater_layout.txt /Users/selva/theater_seating_request.txt"
            + "\n java -jar target\\theater.seating-0.0.1-SNAPSHOT.jar C:\\apps\\theater_layout.txt C:\\apps\\theater_seating_request.txt");
  }
} // end class BatchApplication
