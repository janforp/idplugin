package com.janita.idplugin.idea.base;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;

/**
 * NotificationUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class NotificationUtils {

    public static void showNotification(String content, MessageType messageType) {
        NotificationGroup notificationGroup = new NotificationGroup("Code Review", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(content, messageType);
        notification.setTitle("Code review");
        Notifications.Bus.notify(notification);
    }
}