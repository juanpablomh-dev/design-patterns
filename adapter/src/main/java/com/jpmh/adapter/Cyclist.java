package com.jpmh.adapter;

public final class Cyclist {

  private PedalBike pedalBike;

  public Cyclist() {} 
  
  public Cyclist(PedalBike pedalBike) {
	  this.pedalBike = pedalBike;
  }

  public PedalBike getPedalBike() {
	return pedalBike;
  }

  public void setPedalBike(PedalBike pedalBike) {
	this.pedalBike = pedalBike;
  }

  void pedal() {
	  pedalBike.pedal();
  }

}
