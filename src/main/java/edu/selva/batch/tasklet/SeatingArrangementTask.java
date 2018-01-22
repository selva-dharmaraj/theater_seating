package edu.selva.batch.tasklet;

import edu.selva.batch.pojo.MailInRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.selva.batch.pojo.TheaterLayout;
import edu.selva.batch.util.TicketRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class SeatingArrangementTask implements Tasklet {
  private static final Logger LOGGER = LoggerFactory.getLogger(SeatingArrangementTask.class);
  private TheaterLayout theaterLayout;
  private TicketRequestHandler mailInRequests;

  public SeatingArrangementTask() {}

  public SeatingArrangementTask(TheaterLayout theaterLayout, TicketRequestHandler mailInRequests) {
    this.theaterLayout = theaterLayout;
    this.mailInRequests = mailInRequests;
  }

  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
      throws Exception {
    System.out.println("\n\nTheater layout before seat arrangements:\n");
    theaterLayout.displayTheaterLayout();

    /*
      1. Identify the requests should split
      2. Identify the requests can be accepted
      3. Identify eligible requests should run through system.
    */
    mailInRequests.init(theaterLayout);
    System.out.println("\n\nMail in ticket request summary\n");
    mailInRequests.displayTicketRequestSummary(theaterLayout);

    /*
     1. Start filling seats from the first row using max customer combination without leaving the empty space in the section.
     2. If you could not able to fill any section without leaving an empty space in the section then, move on to next section.
     3. After the entire process is over reiterate until the same step for remaining available seats until no request can be taken.
     4. At this stage, all remaining requests should be split.
     5. Find the groups who can fit by split based on the entire availability.
     6. Send notification to those group members an option to split and accommodate.
     7. Remaining requests should be added to cannot handle request.
    */
    System.out.println("\n\nWorking on seat arrangements...\n");
    theaterLayout
        .getRows()
        .stream()
        .forEach(
            row -> {
              row.getSections()
                  .forEach(
                      section -> {
                        LOGGER.debug(
                            "Working on Section: "
                                + section.getSectionName()
                                + "\tAvailable Seats: "
                                + section.getAvailableSeatsCount());

                        if (mailInRequests.fillEligibleCustomersInSection(row, section)) {
                          section.fillAllSeats();
                        } else if (mailInRequests.isActionTakenOnAllRequests()) {
                          LOGGER.debug("Hooray!!!! all eligible request seats are assigned!!!");
                        }
                      });
            });

    System.out.println("\n\nTheater layout after seat arrangements:\n");
    theaterLayout.displayTheaterLayout();
    LOGGER.info("\n\nOutput:\n");
    mailInRequests.displayTicketRequestResuts();
    System.out.println("\n\n");
    return null;
  }
}
