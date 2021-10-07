/*
	Imagine a cyclist who can only ride a conventional bicycle because he only knows how to pedal.
	With the adapter you can accelerate and ride an electric bike.   
*/
package com.jpmh.adapter;

public class App 
{
    public static void main( String[] args )
    {
	    Cyclist Cyclist = new Cyclist(new ElectricBikeAdapter());
	    Cyclist.pedal();
    }
}
