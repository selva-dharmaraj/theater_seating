package edu.selva.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

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

    SpringApplication.run(BatchApplication.class, args);
  }
}
