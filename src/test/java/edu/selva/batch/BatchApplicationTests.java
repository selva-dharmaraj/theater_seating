package edu.selva.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchApplicationTests {
  private static String OS = System.getProperty("os.name").toLowerCase();
  public static boolean isWindows() {
    return (OS.indexOf("win") >= 0);
  }
  static {
    String filePrefix = "file://";
    if(isWindows()) filePrefix = "file:///";
    System.setProperty("layoutFile", filePrefix + new File("theater_layout.txt").getAbsolutePath());
    System.setProperty(
        "requestFile", filePrefix + new File("theater_seating_request.txt").getAbsolutePath());
  }

  @Test
  public void testJob() {
    System.out.println("TEST method-------------------------------");
  }
}
