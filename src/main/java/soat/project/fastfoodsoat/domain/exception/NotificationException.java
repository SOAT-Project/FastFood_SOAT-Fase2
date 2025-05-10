package soat.project.fastfoodsoat.domain.exception;

import soat.project.fastfoodsoat.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification aNotification) {
        super(aMessage, aNotification.getErrors());
    }

}
