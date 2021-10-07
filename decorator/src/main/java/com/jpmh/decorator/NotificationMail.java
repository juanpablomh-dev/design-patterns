package com.jpmh.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationMail implements Notification {
	private static final Logger logger = LoggerFactory.getLogger(NotificationMail.class);

	public void send() {
		logger.info("send mail...");
	}

}
