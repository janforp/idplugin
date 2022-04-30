package com.janita.idplugin.woodpecker;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import com.janita.idplugin.idea.base.NotificationUtils;

/**
 * TestAction
 *
 * @author zhucj
 * @since 20220324
 */
public class TestAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        NotificationUtils.showNotification("你好", MessageType.ERROR);
    }
}