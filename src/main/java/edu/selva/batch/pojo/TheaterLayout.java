package edu.selva.batch.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
public class TheaterLayout {

  private static final Logger LOGGER = LoggerFactory.getLogger(TheaterLayout.class);
  private String theaterName;
  private List<Row> rows = new ArrayList<>();

  public TheaterLayout() {}

  public String getTheaterName() {
    return theaterName;
  }

  public void setTheaterName(String theaterName) {
    this.theaterName = theaterName;
  }

  public List<Row> getRows() {
    return rows;
  }

  public void setRows(List<Row> rows) {
    this.rows = rows;
  }

  public void addRow(Row row) {
    rows.add(row);
  }

  public int getTotalSeatsInLargestSection() {
    return getRows()
        .stream()
        .max(Comparator.comparingInt(Row::getTotalSeatsInLargestSection))
        .get()
        .getTotalSeatsInLargestSection();
  }

  public int getRowsCount() {
    return rows != null ? rows.size() : 0;
  }

  public int getTotalSeats() {
    return getRows().stream().mapToInt(row -> row.getTotalSeats()).sum();
  }

  public void displayTheaterLayout() {
    this.getRows().stream().forEach(row -> LOGGER.info(String.format("ROW %s", row)));
  }
}
