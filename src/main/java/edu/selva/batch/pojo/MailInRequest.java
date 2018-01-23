package edu.selva.batch.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * MailInRequest domain object.
 *
 * @author Selva Dharmaraj
 * @version 1.0, 2018-01-22
 */
@Component
public class MailInRequest {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger(MailInRequest.class);

  // ~Instance-fields---------------------------------------------------------------------------------------------------

  private boolean accepted;
  private boolean actionTaken;
  private boolean canNotHandle;
  private String customerName;
  private String requestStatus;
  private boolean requireSplit;
  private int rowNumber;
  private int sectionNumber;
  private int ticketsCount;

  // ~Constructors------------------------------------------------------------------------------------------------------

  /** Creates a new MailInRequest object. */
  public MailInRequest() {}

  /**
   * Creates a new MailInRequest object.
   *
   * @param customerName DOCUMENT ME!
   * @param ticketsCount DOCUMENT ME!
   */
  public MailInRequest(String customerName, int ticketsCount) {
    this.customerName = customerName;
    this.ticketsCount = ticketsCount;
  }

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getCustomerName() {
    return customerName;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getRequestStatus() {
    return requestStatus;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getRowNumber() {
    return rowNumber;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getSectionNumber() {
    return sectionNumber;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getTicketsCount() {
    return ticketsCount;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isAccepted() {
    return accepted;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isActionTaken() {
    return actionTaken;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isCanNotHandle() {
    return canNotHandle;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isRequireSplit() {
    return requireSplit;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param accepted DOCUMENT ME!
   */
  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param actionTaken DOCUMENT ME!
   */
  public void setActionTaken(boolean actionTaken) {
    this.actionTaken = actionTaken;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param canNotHandle DOCUMENT ME!
   */
  public void setCanNotHandle(boolean canNotHandle) {
    this.canNotHandle = canNotHandle;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param customerName DOCUMENT ME!
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param requestStatus DOCUMENT ME!
   */
  public void setRequestStatus(String requestStatus) {
    this.requestStatus = requestStatus;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param requireSplit DOCUMENT ME!
   */
  public void setRequireSplit(boolean requireSplit) {
    this.requireSplit = requireSplit;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param rowNumber DOCUMENT ME!
   */
  public void setRowNumber(int rowNumber) {
    this.rowNumber = rowNumber;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param sectionNumber DOCUMENT ME!
   */
  public void setSectionNumber(int sectionNumber) {
    this.sectionNumber = sectionNumber;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param ticketsCount DOCUMENT ME!
   */
  public void setTicketsCount(int ticketsCount) {
    this.ticketsCount = ticketsCount;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String ticketRequestResultToString() {
    return getCustomerName() + " (" + getTicketsCount() + ") - " + getRequestStatus();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /** @see java.lang.Object#toString() */
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
} // end class MailInRequest
