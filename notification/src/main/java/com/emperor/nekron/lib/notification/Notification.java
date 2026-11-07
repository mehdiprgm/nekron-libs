package com.emperor.nekron.lib.notification;

import lombok.Builder;
import lombok.Getter;

import java.awt.*;

/**
 * Provides system tray notification capabilities for desktop applications.
 * <p>
 * This class allows displaying popup notifications through the system tray
 * with customizable title, message, icon, and duration. Notifications are
 * displayed using the native system tray functionality and automatically
 * removed after the specified hold duration.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 25
 */
@Builder(builderMethodName = "internalBuilder")
@Getter
public class Notification {
    private String title;
    private String message;

    private String iconPath;
    private String iconToolTip;

    private TrayIcon.MessageType messageType;
    private Long holdDuration;

    public static class NotificationBuilder {}

    /**
     * Creates a new builder for constructing a notification with the required
     * title, message, and message type.
     *
     * @param title       the notification title
     * @param message     the notification message body
     * @param messageType the type of message which determines the notification icon
     * @return a builder instance pre-populated with the required values
     */
    public static NotificationBuilder builder(String title, String message, TrayIcon.MessageType messageType) {
        return internalBuilder().title(title).message(message).messageType(messageType);
    }

    /**
     * Displays the notification through the system tray and blocks until it is
     * removed.
     * <p>
     * The notification is shown as a system tray message and removed after the
     * configured hold duration. If no hold duration is set, the notification is
     * removed immediately after being displayed.
     *
     * @return {@code true} if the notification was displayed and removed successfully
     * @throws AWTException                  if the system tray is not available or the tray icon
     *                                       cannot be added
     * @throws InterruptedException          if the thread is interrupted while waiting
     *                                       for the hold duration to elapse
     * @throws UnsupportedOperationException if the system tray is not supported
     *                                       on the current platform
     */
    public boolean show() throws AWTException, InterruptedException {
        if (!SystemTray.isSupported()) {
            throw new UnsupportedOperationException("SystemTray is not supported");
        }

        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(iconPath), iconToolTip);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        tray.add(trayIcon);

        trayIcon.displayMessage(title, message, messageType);
        if (holdDuration != null) {
            Thread.sleep(holdDuration);
        }

        tray.remove(trayIcon);
        return true;
    }
}