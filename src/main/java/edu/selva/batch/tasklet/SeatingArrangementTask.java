package edu.selva.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import org.springframework.stereotype.Component;

import edu.selva.batch.pojo.TheaterLayout;
import edu.selva.batch.util.TicketRequestHandler;

/**
 * This Tasklet responsible for Seat arrangement based on theater layout and mail in requests
 * received. This Tasklet perform:
 *
 * <p>1. Capture current layout. 2. Capture current mail in requests. 3. Print the summary of
 * current seat arrangement status. 4. Seat arrangement. 5. Prints the seat arrangement results.
 *
 * @author Selva Dharmaraj
 * @see edu.selva.batch.pojo.TheaterLayout
 * @see edu.selva.batch.util.TicketRequestHandler
 * @version 1.0, 2018-01-22
 */
@Component
public class SeatingArrangementTask implements Tasklet {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger(SeatingArrangementTask.class);

  // ~Instance-fields---------------------------------------------------------------------------------------------------

  private TicketRequestHandler mailInRequests;
  private TheaterLayout theaterLayout;

  // ~Constructors------------------------------------------------------------------------------------------------------

  /** Creates a new SeatingArrangementTask object. */
  public SeatingArrangementTask() {}

  /**
   * Creates a new SeatingArrangementTask object.
   *
   * @param theaterLayout DOCUMENT ME!
   * @param mailInRequests DOCUMENT ME!
   */
  public SeatingArrangementTask(TheaterLayout theaterLayout, TicketRequestHandler mailInRequests) {
    this.theaterLayout = theaterLayout;
    this.mailInRequests = mailInRequests;
  }

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * @see
   *     org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution,
   *     org.springframework.batch.core.scope.context.ChunkContext)
   */
  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
      throws Exception {
    System.out.println("\n\nTheater layout before seat arrangements:\n");
    theaterLayout.displayTheaterLayout();

    /*
    1. Initialize requests.
    2. Identify and remove can't handle and split requests from the evaluation.
    3. Display the current request summary
     */
    mailInRequests.init(theaterLayout);
    System.out.println("\n\nMail in ticket request summary\n");
    mailInRequests.displayTicketRequestSummary(theaterLayout);

    /*
    1. Start filling seats from the first row using max customer combination without leaving the empty space in the section.
    2. If you could not able to fill any section without leaving an empty space in the section then, move on to next section.
    3. After the entire process is over reiterate until the same step for remaining available seats until no request can be taken.
    4. At this stage of calculation, all remaining requests should be split.
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
                        mailInRequests.fillAllSeatsInSection(row, section);
                      });
            });

    // Now look for partial filling with small group requests.

    theaterLayout
        .getRows()
        .stream()
        .forEach(
            row -> {
              row.getSections()
                  .stream()
                  .filter(section -> section.getAvailableSeatsCount() > 0)
                  .forEach(
                      section -> {
                        boolean check = true;
                        while (check) {
                          check = mailInRequests.fillPartialSeatsInSection(row, section);
                        }
                      });
            });

    // set remaining to can't handle
    mailInRequests.moveRequestsRequireActionToCanNotHandleList();

    System.out.println("\n\nTheater layout after seat arrangements:\n");
    theaterLayout.displayTheaterLayout();
    LOGGER.info("\n\nOutput:\n");
    mailInRequests.displayTicketRequestResuts();
    System.out.println("\n\n");

    return null;
  } // end method execute
} // end class SeatingArrangementTask
