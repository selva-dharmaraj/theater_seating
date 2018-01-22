package edu.selva.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchApplicationTests {

  static {
    System.setProperty("layoutFile", "file://" + new File("theater_layout.txt").getAbsolutePath());
    System.setProperty(
        "requestFile", "file://" + new File("theater_seating_request.txt").getAbsolutePath());
  }

  @Test
  public void testJob() {
    System.out.println("TEST method-------------------------------");
  }
}
