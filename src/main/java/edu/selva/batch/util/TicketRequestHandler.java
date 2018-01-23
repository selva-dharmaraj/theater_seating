package edu.selva.batch.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.selva.batch.pojo.MailInRequest;
import edu.selva.batch.pojo.Row;
import edu.selva.batch.pojo.Section;
import edu.selva.batch.pojo.TheaterLayout;

/**
 * This is Utility class holds all mail in requests. There are various function exposed to complete
 * seat arrangement as well display purpose.
 *
 * @author Selva Dharmaraj
 * @see edu.selva.batch.pojo.MailInRequest
 * @see edu.selva.batch.pojo.TheaterLayout
 * @see edu.selva.batch.util.CustomerRequestFinder
 * @version 1.0, 2018-01-22
 */
public class TicketRequestHandler {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger(TicketRequestHandler.class);

  // ~Instance-fields---------------------------------------------------------------------------------------------------

  private List<MailInRequest> mailInRequests;

  // ~Constructors------------------------------------------------------------------------------------------------------

  /** Creates a new TicketRequestHandler object. */
  public TicketRequestHandler() {
    if (this.mailInRequests == null) {
      this.mailInRequests = new ArrayList<>();
    }
  }

  /**
   * Creates a new TicketRequestHandler object.
   *
   * @param mailInRequests DOCUMENT ME!
   */
  public TicketRequestHandler(List<MailInRequest> mailInRequests) {
    this.mailInRequests = mailInRequests;
  }

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param mailInRequest DOCUMENT ME!
   */
  public void addMailInRequest(MailInRequest mailInRequest) {
    this.mailInRequests.add(mailInRequest);
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public void displayTicketRequestResuts() {
    this.getMailInRequests()
        .stream()
        .forEach(
            mailInRequest ->
                LOGGER.info(String.format(mailInRequest.ticketRequestResultToString())));
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * This method prints the summary of mail request status at any given time.
   *
   * @param theaterLayout Theater Layout snapshot.
   */
  public void displayTicketRequestSummary(TheaterLayout theaterLayout) {
    LOGGER.info("Theater seat capacity: " + theaterLayout.getTotalSeats() + " seat(s)");
    LOGGER.info(
        "Requested seats: "
            + getMailInRequests().size()
            + " request(s) and "
            + getTotalRequestedTickets()
            + " seat(s)");
    LOGGER.info(
        "Can't handle seats: "
            + getCanNotHandleRequests().size()
            + " request(s) and "
            + getCanNotHandleRequestTicketsCount()
            + " seat(s)");
    LOGGER.info(
        "Call to split seats: "
            + getCallUsToSplitRequests().size()
            + " request(s) and "
            + getCallUsToSplitTicketsCount()
            + " seat(s)");
    LOGGER.info(
        "Total eligible seats: "
            + getRequestsCountRequiresAction()
            + " request(s) "
            + getRequestsCountRequiresAction()
            + " seat(s)");
  } // end method displayTicketRequestSummary

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * This is critical method which find eligible mail in requests for the given Section. This
   * methods try to fit the given section with one or more customer request. The goal of the filling
   * process is not to lave empty space in given section. Once the algorithm finds the eligible
   * customer request then it will perform status update and remove it from rest of the calculation.
   *
   * @param row given row in the theater.
   * @param section given section in the theater.
   * @return boolean - if the section is filled completely returns true or return false.
   */
  public boolean fillAllSeatsInSection(Row row, Section section) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.info(
          "Working on filling Section: "
              + section.getSectionName()
              + "\tAvailable Seats: "
              + section.getAvailableSeatsCount());
    }

    List<Integer> requestsFitInSection =
        CustomerRequestFinder.findEligibleCustomerRequests(
            getRequestRequiresActionInArray(), section.getAvailableSeatsCount());

    if (requestsFitInSection != null) {
      requestsFitInSection
          .stream()
          .forEach(
              ticketsCount -> {
                getRequestsRequireAction()
                    .stream()
                    .filter(mailInRequest -> mailInRequest.getTicketsCount() == ticketsCount)
                    .findFirst()
                    .ifPresent(
                        mailInRequest -> {
                          mailInRequest.setAccepted(true);
                          mailInRequest.setActionTaken(true);
                          mailInRequest.setRowNumber(row.getRowNumber());
                          mailInRequest.setSectionNumber(section.getSectionNumber());
                          mailInRequest.setRequestStatus(
                              "Row "
                                  + row.getRowNumber()
                                  + " Section "
                                  + section.getSectionNumber());
                        });
              });
      section.fillAllSeats();
      return true;
    }

    return false;
  } // end method fillAllSeatsInSection

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * This is will try to fill remaining section with partial eligible seats.
   *
   * @param row given row in the theater.
   * @param section given section in the theater.
   * @return boolean - if the section is filled partially returns true or return false.
   */
  public boolean fillPartialSeatsInSection(Row row, Section section) {

    if (LOGGER.isDebugEnabled()) {
      LOGGER.info(
          "Working on filling partial Section: "
              + section.getSectionName()
              + "\tAvailable Seats: "
              + section.getAvailableSeatsCount());
    }

    int availableSeats = section.getAvailableSeatsCount();
    AtomicBoolean returnValue = new AtomicBoolean(false);
    if (availableSeats > 0) {
      getRequestsRequireAction()
          .stream()
          .filter(mailInRequest -> mailInRequest.getTicketsCount() <= availableSeats)
          .findFirst()
          .ifPresent(
              mailInRequest -> {
                System.out.println("Found -> " + mailInRequest);
                mailInRequest.setAccepted(true);
                mailInRequest.setActionTaken(true);
                mailInRequest.setRowNumber(row.getRowNumber());
                mailInRequest.setSectionNumber(section.getSectionNumber());
                mailInRequest.setRequestStatus(
                    "Row " + row.getRowNumber() + " Section " + section.getSectionNumber());
                section.fillSeats(mailInRequest.getTicketsCount());
                returnValue.set(true);
              });
    }

    return returnValue.get();
  } // end method fillAllSeatsInSection

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<MailInRequest> getCallUsToSplitRequests() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> mailInRequest.isRequireSplit())
        .collect(Collectors.toList());
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getCallUsToSplitTicketsCount() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> mailInRequest.isRequireSplit())
        .mapToInt(mailInRequest -> mailInRequest.getTicketsCount())
        .sum();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<MailInRequest> getCanNotHandleRequests() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> mailInRequest.isCanNotHandle())
        .collect(Collectors.toList());
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getCanNotHandleRequestTicketsCount() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> mailInRequest.isCanNotHandle())
        .mapToInt(mailInRequest -> mailInRequest.getTicketsCount())
        .sum();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<MailInRequest> getMailInRequests() {
    return mailInRequests;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<MailInRequest> getRequestsRequireAction() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> !mailInRequest.isActionTaken())
        .collect(Collectors.toList());
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getRequestsCountRequiresAction() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> !mailInRequest.isActionTaken())
        .mapToInt(mailRequest -> mailRequest.getTicketsCount())
        .sum();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getTotalRequestedTickets() {
    return this.mailInRequests
        .stream()
        .mapToInt(mailInRequest -> mailInRequest.getTicketsCount())
        .sum();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * This is primary method which makes identifies the 'can't handle' and 'call us to split'
   * requests. Such requests are marked appropriately.
   *
   * @param theaterLayout Theater Layout loaded from input file.
   */
  public void init(TheaterLayout theaterLayout) {
    int theaterCapacity = theaterLayout.getTotalSeats();
    int seatsInLargestSection = theaterLayout.getTotalSeatsInLargestSection();

    mailInRequests
        .stream()
        .filter(mailInRequest -> mailInRequest.getTicketsCount() > theaterCapacity)
        .forEach(
            mailInRequest -> {
              mailInRequest.setCanNotHandle(true);
              mailInRequest.setActionTaken(true);
              mailInRequest.setRequestStatus("Sorry, we can't handle your party.");
            });

    mailInRequests
        .stream()
        .filter(mailInRequest -> !mailInRequest.isCanNotHandle())
        .filter(mailInRequest -> mailInRequest.getTicketsCount() > seatsInLargestSection)
        .forEach(
            mailInRequest -> {
              mailInRequest.setRequireSplit(true);
              mailInRequest.setActionTaken(true);
              mailInRequest.setRequestStatus("Call to split party.");
            });
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isActionTakenOnAllRequests() {
    return mailInRequests.stream().allMatch(mailInRequest -> mailInRequest.isActionTaken());
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param mailInRequests DOCUMENT ME!
   */
  public void setMailInRequests(List<MailInRequest> mailInRequests) {
    this.mailInRequests = mailInRequests;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  private int[] getRequestRequiresActionInArray() {
    return getRequestsRequireAction().stream().mapToInt(MailInRequest::getTicketsCount).toArray();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  public void moveRequestsRequireActionToCanNotHandleList() {
    getRequestsRequireAction()
        .stream()
        .forEach(
            mailInRequest -> {
              mailInRequest.setCanNotHandle(true);
              mailInRequest.setActionTaken(true);
              mailInRequest.setRequestStatus("Sorry, we can't handle your party.");
            });
  }
} // end class TicketRequestHandler
