/*
	Imagine that you have to create different types of SubWay sandwiches.
	Creating a construct for each type would be inelegant and it would also be very difficult 
	to know the order of the parameters.
	
	With the builder, I was able to create the base structure of the sandwich and add the components I want.
*/
package com.jpmh.builder;

import java.util.logging.Logger;

public class App {
	static Logger logger = Logger.getLogger(App.class.getName());
	
    public static void main( String[] args ){

		SubWay single = new SubWay.Builder(BreadType.WHITE, MeatType.BEEF).withMayonnaise(true).withLettuce(true)
				.withTomato(true).build();
		logger.info(single.toString());

		SubWay italian = new SubWay.Builder(BreadType.ITALIAN, MeatType.CHICKEN).withMayonnaise(true).withKetchup(true)
				.withLettuce(true).withTomato(true).withBacon(true).withCheese(true).withOnion(true).build();
		logger.info(italian.toString());

		SubWay sandwich = new SubWay.Builder(BreadType.WHOLEMEAL, MeatType.FISH).withHam(true).withCheese(true)
				.withKetchup(true).withLettuce(true).withTomato(true).withOnion(true).withPickle(true).build();
		logger.info(sandwich.toString());
	}
}
