package com.jpmh.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class BuilderPatternTest {

  /**
   * Try creating a SubWay without the breadType parameter of the constructor
   */
  @Test
  void testMissingBreadType() {
    assertThrows(IllegalArgumentException.class, () -> new SubWay.Builder(null, MeatType.BEEF));
  }

  /**
   * Try creating a SubWay without the meatType parameter of the constructor
   */
  @Test
  void testMissingMeatType() {
    assertThrows(IllegalArgumentException.class, () -> new SubWay.Builder(BreadType.ITALIAN, null));
  }

  /**
   * Test if the SubWay build by the builder has the correct attributes, as requested
   */
  @Test
  void testBuildSubWay() {
    
    final SubWay subWay = new SubWay.Builder(BreadType.ITALIAN, MeatType.BEEF)
        .withBacon(true)
        .withCheese(true)
        .withHam(true)
        .withKetchup(true)
        .withLettuce(true)
        .withMayonnaise(false)
        .withMoztaza(false)
        .withOnion(false)
        .withPickle(false)
        .withTomato(false)
        .build();

    assertNotNull(subWay);
    assertNotNull(subWay.toString());
    assertEquals(BreadType.ITALIAN, subWay.getBreadType());
    assertEquals(MeatType.BEEF, subWay.getMeatType());
    assertEquals(true, subWay.getBacon());
    assertEquals(true, subWay.getCheese());
    assertEquals(true, subWay.getHam());
    assertEquals(true, subWay.getKetchup());
    assertEquals(true, subWay.getLettuce());
    assertEquals(false, subWay.getMayonnaise());
    assertEquals(false, subWay.getMoztaza());
    assertEquals(false, subWay.getOnion());
    assertEquals(false, subWay.getPickle());
    assertEquals(false, subWay.getTomato());
  }

}