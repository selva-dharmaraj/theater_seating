package edu.selva.batch.pojo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * Row domain object.
 *
 * @author Selva Dharmaraj
 * @version 1.0, 2018-01-22
 */
@Component
public class Row {
  // ~Instance-fields---------------------------------------------------------------------------------------------------

  private int rowNumber;
  private List<Section> sections = new ArrayList<>();

  // ~Constructors------------------------------------------------------------------------------------------------------

  /** Creates a new Row object. */
  public Row() {}

  /**
   * Creates a new Row object.
   *
   * @param rowNumber DOCUMENT ME!
   */
  public Row(int rowNumber) {
    this.rowNumber = rowNumber;
  }

  // ~Methods-----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param section DOCUMENT ME!
   */
  public void addSection(Section section) {
    this.sections.add(section);
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
  public List<Section> getSections() {
    return sections;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getTotalSeats() {
    return getSections().stream().mapToInt(section -> section.getTotalSeats()).sum();
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getTotalAvailableSeats() {
    return getSections().stream().mapToInt(section -> section.getAvailableSeatsCount()).sum();
  }

  public List<Section> getSectionsWithAvailableSeats(){
    return sections.stream().filter(section -> section.getAvailableSeatsCount()>0).collect(Collectors.toList());
  }
  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getTotalSeatsInLargestSection() {
    return getSections()
        .stream()
        .max(Comparator.comparingInt(Section::getTotalSeats))
        .get()
        .getTotalSeats();
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
   * @param sections DOCUMENT ME!
   */
  public void setSections(List<Section> sections) {
    this.sections = sections;
  }

  // ~------------------------------------------------------------------------------------------------------------------

  /** @see java.lang.Object#toString() */
  @Override
  public String toString() {
    return rowNumber + "{" + sections + "}";
  }
} // end class Row
