package edu.selva.batch.pojo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Theaterlayout domain object.
 *
 * @author Selva Dharmaraj
 * @version 1.0, 2018-01-22
 */
@Component
public class TheaterLayout {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger(TheaterLayout.class);

  // ~Instance-fields---------------------------------------------------------------------------------------------------

  private List<Row> rows = new ArrayList<>();
  private String theaterName;

  // ~Constructors------------------------------------------------------------------------------------------------------

  /** Creates a new TheaterLayout object. */
  public TheaterLayout() {}

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param row DOCUMENT ME!
   */
  public void addRow(Row row) {
    rows.add(row);
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public void displayTheaterLayout() {
    this.getRows().stream().forEach(row -> LOGGER.info(String.format("ROW %s", row)));
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<Row> getRows() {
    return rows;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getRowsCount() {
    return (rows != null) ? rows.size() : 0;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getTheaterName() {
    return theaterName;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getTotalSeats() {
    return getRows().stream().mapToInt(row -> row.getTotalSeats()).sum();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getTotalAvailableSeats() {
    return getRows().stream().mapToInt(row -> row.getTotalAvailableSeats()).sum();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getTotalSeatsInLargestSection() {
    return getRows()
        .stream()
        .max(Comparator.comparingInt(Row::getTotalSeatsInLargestSection))
        .get()
        .getTotalSeatsInLargestSection();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param rows DOCUMENT ME!
   */
  public void setRows(List<Row> rows) {
    this.rows = rows;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param theaterName DOCUMENT ME!
   */
  public void setTheaterName(String theaterName) {
    this.theaterName = theaterName;
  }

  public void updateSeatsAvailabilityForSplitRequests(int tickets) {
    LOGGER.debug("Fill Split Requests count: " + tickets);
    AtomicInteger splitTiketsFilled = new AtomicInteger();
    getRows()
        .stream()
        .filter(row -> row.getTotalAvailableSeats() > 0)
        .anyMatch(
            row -> {
              if (tickets == splitTiketsFilled.get()) {
                LOGGER.debug("Tickets all filled.");
                return true;
              }
              LOGGER.debug("Row: " + row);
              row.getSectionsWithAvailableSeats()
                  .stream()
                  .anyMatch(
                      section -> {
                        if (tickets == splitTiketsFilled.get()) {
                          LOGGER.debug("Tickets all filled.");
                          return true;
                        }
                        LOGGER.debug(
                            "Ticket to be marked: "
                                + tickets
                                + "  filled so far: "
                                + splitTiketsFilled.get());
                        int availableSeats = section.getAvailableSeatsCount();

                        if (availableSeats <= (tickets - splitTiketsFilled.get())) {
                          LOGGER.debug("Filling entire section: " + section);
                          section.fillAllSplitSeats();
                          splitTiketsFilled.addAndGet(availableSeats);
                          LOGGER.debug("Filling entire section: " + section);
                        } else {
                          LOGGER.debug("Fill partial seats for split: " + section);
                          section.fillSplitSeats(tickets - splitTiketsFilled.get());
                          splitTiketsFilled.addAndGet(tickets - splitTiketsFilled.get());
                          LOGGER.debug("Fill partial seats for split: " + section);
                        }
                        return false;
                      });
              LOGGER.debug("Row: " + row);
              return false;
            });
  }
} // end class TheaterLayout
