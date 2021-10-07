package com.jpmh.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

/**
 * Tests for {@link NotificationMail}
 */
class NotificationMailTest {

  private InMemoryAppender appender;

  @BeforeEach
  void setUp() {
    appender = new InMemoryAppender(NotificationMail.class);
  }

  @AfterEach
  void tearDown() {
    appender.stop();
  }

  @Test
  void testNotificationActions() {
    final NotificationMail notificationMail = new NotificationMail();
    notificationMail.send();
    assertEquals("send mail...", appender.getLastMessage());    
    assertEquals(1, appender.getLogSize());
  }

  private class InMemoryAppender extends AppenderBase<ILoggingEvent> {

    private final List<ILoggingEvent> log = new LinkedList<>();

    InMemoryAppender(Class clase) {
      ((Logger) LoggerFactory.getLogger(clase)).addAppender(this);
      start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      log.add(eventObject);
    }

    String getLastMessage() {
      return log.get(log.size() - 1).getMessage();
    }

    int getLogSize() {
      return log.size();
    }
  }
}
