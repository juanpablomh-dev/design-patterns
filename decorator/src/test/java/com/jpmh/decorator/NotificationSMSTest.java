package com.jpmh.decorator;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link NotificationSMS}
 */
class NotificationSMSTest {

  @Test
  void testNotificationSMS() {
    // Create a NotificatioMail first, but make sure we can spy on it later on.
    final NotificationMail notificationMail = spy(new NotificationMail());

    // Now we want to decorate the NotificationSMS with NotificatioMail
    final NotificationSMS notificationSMS = new NotificationSMS(notificationMail);
    notificationSMS.send();
    verify(notificationMail, times(1)).send();
	verifyNoMoreInteractions(notificationMail);
  }
}
