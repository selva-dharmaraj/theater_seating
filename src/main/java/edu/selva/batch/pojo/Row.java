package edu.selva.batch.pojo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * Row domain object.
 *
 * @author Selva Dharmaraj
 * @since 2018-01-22
 */

@Component
public class Row {

  private int rowNumber;
  private List<Section> sections = new ArrayList<>();

  public Row() {}

  public Row(int rowNumber) {
    this.rowNumber = rowNumber;
  }

  public int getRowNumber() {
    return rowNumber;
  }

  public void setRowNumber(int rowNumber) {
    this.rowNumber = rowNumber;
  }

  public List<Section> getSections() {
    return sections;
  }

  public void setSections(List<Section> sections) {
    this.sections = sections;
  }

  public void addSection(Section section) {
    this.sections.add(section);
  }

  public int getTotalSeats() {
    return getSections().stream().mapToInt(section -> section.getTotalSeats()).sum();
  }

  public int getTotalSeatsInLargestSection() {
    return getSections()
        .stream()
        .max(Comparator.comparingInt(Section::getTotalSeats))
        .get()
        .getTotalSeats();
  }

  @Override
  public String toString() {
    return rowNumber + "{" + sections + "}";
  }
}
