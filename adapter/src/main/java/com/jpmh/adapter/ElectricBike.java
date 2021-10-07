package com.jpmh.adapter;

import java.util.logging.Logger;

final class ElectricBike {

	Logger logger = Logger.getLogger(ElectricBike.class.getName());

	void accelerate() {
		logger.info("The cyclist is accelerating ...");
	}

}
