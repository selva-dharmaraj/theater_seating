package edu.selva.batch.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * MailInRequest domain object.
 *
 * @author Selva Dharmaraj
 * @since 2018-01-22
 */
@Component
public class MailInRequest {
  private static final Logger LOGGER = LoggerFactory.getLogger(MailInRequest.class);
  private String customerName;
  private int ticketsCount;
  private boolean accepted;
  private boolean canNotHandle;
  private boolean requireSplit;
  private String requestStatus;
  private boolean actionTaken;
  private int rowNumber;
  private int sectionNumber;

  public MailInRequest() {}

  public MailInRequest(String customerName, int ticketsCount) {
    this.customerName = customerName;
    this.ticketsCount = ticketsCount;
  }

  public int getRowNumber() {
    return rowNumber;
  }

  public void setRowNumber(int rowNumber) {
    this.rowNumber = rowNumber;
  }

  public int getSectionNumber() {
    return sectionNumber;
  }

  public void setSectionNumber(int sectionNumber) {
    this.sectionNumber = sectionNumber;
  }

  public boolean isActionTaken() {
    return actionTaken;
  }

  public void setActionTaken(boolean actionTaken) {
    this.actionTaken = actionTaken;
  }

  public boolean isCanNotHandle() {
    return canNotHandle;
  }

  public void setCanNotHandle(boolean canNotHandle) {
    this.canNotHandle = canNotHandle;
  }

  public boolean isRequireSplit() {
    return requireSplit;
  }

  public void setRequireSplit(boolean requireSplit) {
    this.requireSplit = requireSplit;
  }

  public String getRequestStatus() {
    return requestStatus;
  }

  public void setRequestStatus(String requestStatus) {
    this.requestStatus = requestStatus;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public int getTicketsCount() {
    return ticketsCount;
  }

  public void setTicketsCount(int ticketsCount) {
    this.ticketsCount = ticketsCount;
  }

  @Override
  public String toString() {
    return "MailInRequest{"
        + "customerName='"
        + customerName
        + '\''
        + ", ticketsCount="
        + ticketsCount
        + ", accepted="
        + accepted
        + ", canNotHandle="
        + canNotHandle
        + ", requireSplit="
        + requireSplit
        + ", requestStatus='"
        + requestStatus
        + '\''
        + '}';
  }

  public String ticketRequestResultToString() {
    return getCustomerName() + " " + getRequestStatus();
  }
}
