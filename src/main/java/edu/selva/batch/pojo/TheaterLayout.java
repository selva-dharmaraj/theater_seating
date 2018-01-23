package edu.selva.batch.pojo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
} // end class TheaterLayout
