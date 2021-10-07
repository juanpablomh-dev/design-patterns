package com.jpmh.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationSMS implements Notification {

	private static final Logger logger = LoggerFactory.getLogger(NotificationSMS.class);

	private final Notification decorated;

	public NotificationSMS(Notification decorated) {
		this.decorated = decorated;
	}

	public Notification getDecorated() {
		return decorated;
	}

	public void send() {
		decorated.send();
		logger.info("send sms...");
	}
}
