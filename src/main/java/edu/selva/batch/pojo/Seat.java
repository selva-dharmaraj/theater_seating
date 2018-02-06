package edu.selva.batch.pojo;

import org.springframework.stereotype.Component;

/**
 * Seat domain object.
 *
 * @author Selva Dharmaraj
 * @version 1.0, 2018-01-22
 */
@Component
public class Seat {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  public static final String EMPTY = "__";
  public static final String FILLED = "R";
  public static final String SPLIT = "W";

  // ~Instance-fields---------------------------------------------------------------------------------------------------

  private String seatAvailable = EMPTY;

  private String seatName;

  // ~Constructors------------------------------------------------------------------------------------------------------

  /** Creates a new Seat object. */
  public Seat() {}

  /**
   * Creates a new Seat object.
   *
   * @param seatNumber DOCUMENT ME!
   */
  public Seat(String seatNumber) {
    this.seatName = seatNumber;
  }

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getSeatAvailable() {
    return seatAvailable;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getSeatName() {
    return seatName;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param seatAvailable DOCUMENT ME!
   */
  public void setSeatAvailable(String seatAvailable) {
    this.seatAvailable = seatAvailable;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param seatName DOCUMENT ME!
   */
  public void setSeatName(String seatName) {
    this.seatName = seatName;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /** @see java.lang.Object#toString() */
  @Override
  public String toString() {
    return seatAvailable;
  }
} // end class Seat
