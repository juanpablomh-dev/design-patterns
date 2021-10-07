package com.jpmh.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ){
    	notification();
    }
    
    public static void notification(){
		logger.info("send notification by mail...");
		Notification notification = new NotificationMail();
		notification.send();

		// change the behavior of the email notification by adding a decorator
		logger.info("send notification by mail and sms");
		NotificationSMS notificationSMS = new NotificationSMS(notification);
		notificationSMS.send();	    	
    }
}
