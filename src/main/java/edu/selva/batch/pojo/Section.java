package edu.selva.batch.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * Section domain object.
 *
 * @author Selva Dharmaraj
 * @version 1.0, 2018-01-22
 */
@Component
public class Section {
  // ~Instance-fields---------------------------------------------------------------------------------------------------

  private List<Seat> seats = new ArrayList<>();
  private String sectionName;

  private int sectionNumber;

  // ~Constructors------------------------------------------------------------------------------------------------------

  /** Creates a new Section object. */
  public Section() {}

  /**
   * Creates a new Section object.
   *
   * @param sectionName DOCUMENT ME!
   */
  public Section(String sectionName) {
    this.sectionName = sectionName;
  }

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param seat DOCUMENT ME!
   */
  public void addSeat(Seat seat) {
    this.seats.add(seat);
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<Seat> fillAllSeats() {
    List<Seat> returnList = null;
    returnList = getAvailableSeats().stream().collect(Collectors.toList());
    returnList.stream().forEach(e -> e.setSeatAvailable(Seat.FILLED));

    return returnList;
  }

  public List<Seat> fillAllSplitSeats() {
    List<Seat> returnList = null;
    returnList = getAvailableSeats().stream().collect(Collectors.toList());
    returnList.stream().forEach(e -> e.setSeatAvailable(Seat.SPLIT));

    return returnList;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param fillCount DOCUMENT ME!
   * @return DOCUMENT ME!
   */
  public List<Seat> fillSeats(int fillCount) {
    List<Seat> returnList = null;

    if (fillCount < getAvailableSeatsCount()) {
      returnList = getAvailableSeats().stream().limit(fillCount).collect(Collectors.toList());
      returnList.stream().limit(fillCount).forEach(e -> e.setSeatAvailable(Seat.FILLED));
    }

    return returnList;
  }

  public List<Seat> fillSplitSeats(int fillCount) {
    List<Seat> returnList = null;

    if (fillCount < getAvailableSeatsCount()) {
      returnList = getAvailableSeats().stream().limit(fillCount).collect(Collectors.toList());
      returnList.stream().limit(fillCount).forEach(e -> e.setSeatAvailable(Seat.SPLIT));
    }

    return returnList;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<Seat> getAvailableSeats() {
    return seats
        .stream()
        .filter(seat -> seat.getSeatAvailable().equals(Seat.EMPTY))
        .collect(Collectors.toList());
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getAvailableSeatsCount() {
    return (int) seats.stream().filter(seat -> seat.getSeatAvailable().equals(Seat.EMPTY)).count();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<Seat> getSeats() {
    return seats;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getSectionName() {
    return sectionName;
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
  public int getTotalSeats() {
    return (seats != null) ? seats.size() : 0;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param seats DOCUMENT ME!
   */
  public void setSeats(List<Seat> seats) {
    this.seats = seats;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param sectionName DOCUMENT ME!
   */
  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
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

  /** @see java.lang.Object#toString() */
  @Override
  public String toString() {
    return "" + sectionNumber + seats;
  }
} // end class Section
