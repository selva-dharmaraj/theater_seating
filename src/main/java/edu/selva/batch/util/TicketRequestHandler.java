package edu.selva.batch.util;

import edu.selva.batch.pojo.MailInRequest;
import edu.selva.batch.pojo.Row;
import edu.selva.batch.pojo.Section;
import edu.selva.batch.pojo.TheaterLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketRequestHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(TicketRequestHandler.class);
  private List<MailInRequest> mailInRequests;

  public TicketRequestHandler(List<MailInRequest> mailInRequests) {
    this.mailInRequests = mailInRequests;
  }

  public TicketRequestHandler() {
    if (this.mailInRequests == null) {
      this.mailInRequests = new ArrayList<>();
    }
  }

  public List<MailInRequest> getMailInRequests() {
    return mailInRequests;
  }

  public void setMailInRequests(List<MailInRequest> mailInRequests) {
    this.mailInRequests = mailInRequests;
  }

  public void addMailInRequest(MailInRequest mailInRequest) {
    this.mailInRequests.add(mailInRequest);
  }

  public int getTotalRequestedTickets() {
    return this.mailInRequests
        .stream()
        .mapToInt(mailInRequest -> mailInRequest.getTicketsCount())
        .sum();
  }

  public List<MailInRequest> getCanNotHandleRequests() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> mailInRequest.isCanNotHandle())
        .collect(Collectors.toList());
  }

  public int getCanNotHandleRequestTicketsCount() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> mailInRequest.isCanNotHandle())
        .mapToInt(mailInRequest -> mailInRequest.getTicketsCount())
        .sum();
  }

  public List<MailInRequest> getCallUsToSplitRequests() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> mailInRequest.isRequireSplit())
        .collect(Collectors.toList());
  }

  public int getCallUsToSplitTicketsCount() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> mailInRequest.isRequireSplit())
        .mapToInt(mailInRequest -> mailInRequest.getTicketsCount())
        .sum();
  }

  public List<MailInRequest> getRequestsCanBeAccepted() {

    return mailInRequests
        .stream()
        .filter(mailInRequest -> !mailInRequest.isRequireSplit())
        .filter(mailInRequest -> !mailInRequest.isCanNotHandle())
        .filter(mailInRequest -> !mailInRequest.isAccepted())
        .collect(Collectors.toList());
  }

  public boolean isActionTakenOnAllRequests() {
    return mailInRequests.stream().allMatch(mailInRequest -> mailInRequest.isActionTaken());
  }

  public int getRequestsCountRequiresAction() {
    return mailInRequests
        .stream()
        .filter(mailInRequest -> !mailInRequest.isActionTaken())
        .mapToInt(mailRequest -> mailRequest.getTicketsCount())
        .sum();
  }

  private List<Integer> getEligibleCustomerTickets() {
    List<Integer> ticketsList =
        getRequestsCanBeAccepted()
            .stream()
            .map(MailInRequest::getTicketsCount)
            .collect(Collectors.toList());
    return ticketsList;
  }

  public boolean fillEligibleCustomersInSection(Row row, Section section) {

    List<Integer> eligibleCustomerRequests =
        CustomerRequestFinder.findEligibleCustomerRequests(
            getEligibleCustomerTickets(), section.getAvailableSeatsCount());

    if (eligibleCustomerRequests != null) {
      eligibleCustomerRequests
          .stream()
          .forEach(
              integer -> {
                getRequestsCanBeAccepted()
                    .stream()
                    .filter(mailInRequest -> mailInRequest.getTicketsCount() == integer)
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
      return true;
    }
    return false;
  }

  public void displayTicketRequestResuts() {
    this.getMailInRequests()
        .stream()
        .forEach(
            mailInRequest ->
                LOGGER.info(String.format(mailInRequest.ticketRequestResultToString())));
  }

  public void init(int theaterCapacity, int seatsInLargestSection) {}

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
            + getEligibleCustomerTickets().size()
            + " request(s) "
            + getRequestsCountRequiresAction()
            + " seat(s)");
  }
}
