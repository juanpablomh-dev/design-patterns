package com.jpmh.adapter;

public class ElectricBikeAdapter implements PedalBike {

  private final ElectricBike bike = new ElectricBike();

  public final void pedal() {
	  bike.accelerate();
  }
}
