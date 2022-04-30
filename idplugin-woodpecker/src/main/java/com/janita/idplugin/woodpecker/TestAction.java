package com.janita.idplugin.woodpecker;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import com.janita.idplugin.remote.RemoteApplication;

/**
 * TestAction
 *
 * @author zhucj
 * @since 20220324
 */
public class TestAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        RemoteApplication.main(null);

        NotificationGroup notificationGroup = new NotificationGroup("Code Review", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification("content", MessageType.INFO);
        notification.setTitle("Code review");
        Notifications.Bus.notify(notification);    }
}
