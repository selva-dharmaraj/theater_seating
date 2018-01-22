package edu.selva.batch.pojo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Section {

  private int sectionNumber;
  private String sectionName;
  private List<Seat> seats = new ArrayList<>();

  public Section() {}

  public Section(String sectionName) {
    this.sectionName = sectionName;
  }

  public int getSectionNumber() {
    return sectionNumber;
  }

  public void setSectionNumber(int sectionNumber) {
    this.sectionNumber = sectionNumber;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public List<Seat> getSeats() {
    return seats;
  }

  public void setSeats(List<Seat> seats) {
    this.seats = seats;
  }

  public void addSeat(Seat seat) {
    this.seats.add(seat);
  }

  public int getTotalSeats() {
    return seats != null ? seats.size() : 0;
  }

  public int getAvailableSeatsCount() {
    return (int) seats.stream().filter(seat -> seat.getSeatAvailable().equals("__")).count();
  }

  public List<Seat> getAvailableSeats() {
    return seats
        .stream()
        .filter(seat -> seat.getSeatAvailable().equals("__"))
        .collect(Collectors.toList());
  }

  public List<Seat> fillSeats(int fillCount) {
    List<Seat> returnList = null;
    if (fillCount < getAvailableSeatsCount()) {

      returnList = getAvailableSeats().stream().limit(fillCount).collect(Collectors.toList());
      returnList.stream().limit(fillCount).forEach(e -> e.setSeatAvailable("X"));
    }
    return returnList;
  }

  public List<Seat> fillAllSeats() {
    List<Seat> returnList = null;
    returnList = getAvailableSeats().stream().collect(Collectors.toList());
    returnList.stream().forEach(e -> e.setSeatAvailable("X"));
    return returnList;
  }

  @Override
  public String toString() {
    return "" + sectionNumber + seats;
  }
}
