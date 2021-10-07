package com.jpmh.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Test class
 */
class AdapterTest {

  private Map<String, Object> beans;
  private static final String ELECTRIC_BEAN = "electric";
  private static final String PEDAL_BEAN = "pedal";

  /**
   * This method runs before the test execution and sets the bean objects in the beans Map.
   */
  @BeforeEach
  public void setup() {
	  beans = new HashMap<>();
	
	  ElectricBikeAdapter electricBikeAdapter = spy(new ElectricBikeAdapter());
	  beans.put(ELECTRIC_BEAN, electricBikeAdapter);
	
	  Cyclist cyclist = new Cyclist();
	  cyclist.setPedalBike((ElectricBikeAdapter) beans.get(ELECTRIC_BEAN));
	  beans.put(PEDAL_BEAN, cyclist);
  }

  /**
   * This test asserts that when we use the row() method on a captain bean(client), it is internally
   * calling sail method on the fishing boat object. The Adapter ({@link FishingBoatAdapter} )
   * converts the interface of the target class ( {@link FishingBoat}) into a suitable one expected
   * by the client ({@link Captain} ).
   */
   @Test
   void testAdapter() {
	   Cyclist cyclist = (Cyclist) beans.get(PEDAL_BEAN);

       // when cyclist pedal
	   cyclist.pedal();

       // the cyclist calls the electric bike through the adapter to move
	   PedalBike adapter = (PedalBike) beans.get(ELECTRIC_BEAN);
       verify(adapter).pedal();
  }
}
