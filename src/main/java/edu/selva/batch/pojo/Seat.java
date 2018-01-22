package edu.selva.batch.pojo;

import org.springframework.stereotype.Component;

@Component
public class Seat {

  private String seatName;
  private String seatAvailable = "__";

  public Seat() {}

  public String getSeatAvailable() {
    return seatAvailable;
  }

  public void setSeatAvailable(String seatAvailable) {
    this.seatAvailable = seatAvailable;
  }

  public Seat(String seatNumber) {
    this.seatName = seatNumber;
  }

  public String getSeatName() {
    return seatName;
  }

  public void setSeatName(String seatName) {
    this.seatName = seatName;
  }

  @Override
  public String toString() {
    return seatAvailable;
  }
}
