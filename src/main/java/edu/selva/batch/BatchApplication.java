package edu.selva.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * This is SprintBootApplication class. This application takes two inputs as String argument. This
 * Class initialize require context and starts up <b>theaterSeatingJob</b> batch job for loading
 * input file data in to memory and seat arrangement task.
 *
 * @author Selva Dharmaraj
 * @since   2018-01-22
 * @see edu.selva.batch.configuration.TheaterSeatingJobConfiguration
 */
@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {

  public static void main(String[] args) throws Exception {

    if (args.length > 1) {
      try {
        System.setProperty("layoutFile", "file://" + new File(args[0]).getAbsolutePath());
        System.setProperty("requestFile", "file://" + new File(args[1]).getAbsolutePath());
      } catch (NumberFormatException e) {
        System.err.println("Error in parsing arguments. Please verify your input files location.");
        System.err.println();
        System.err.println(
            "Argument Error:"
                + "\n Usage: java -jar <jar_file> <layout_file> <request_file>"
                + "\n Example: java -jar target/theater.seating-0.0.1-SNAPSHOT.jar /Users/selva/theater_layout.txt /Users/selva/theater_seating_request.txt"
                + "\n Example: java -jar target/theater.seating-0.0.1-SNAPSHOT.jar theater_layout.txt theater_seating_request.txt");

        System.exit(1);
      }
    } else {
      System.err.println(
          "Argument Error:"
              + "\n Usage: java -jar <jar_file> <layout_file> <request_file>"
              + "\n Example: java -jar target/theater.seating-0.0.1-SNAPSHOT.jar /Users/selva/theater_layout.txt /Users/selva/theater_seating_request.txt"
              + "\n Example: java -jar target/theater.seating-0.0.1-SNAPSHOT.jar theater_layout.txt theater_seating_request.txt");
      System.exit(1);
    }
    // Run Spring batch job
    SpringApplication.run(BatchApplication.class, args);
  }
}
