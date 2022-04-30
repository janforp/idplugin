package com.janita.idplugin.demo.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBLabel;
import com.janita.idplugin.idea.base.NotificationUtils;

import javax.swing.*;

/**
 * EditorTextFieldAction
 *
 * @author zhucj
 * @since 20220324
 */
public class EditorTextFieldAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            NotificationUtils.showNotification("editor 为空", MessageType.ERROR);
            return;
        }
        Document document = editor.getDocument();
        EditorTextField editorTextField = new EditorTextField(document, project, StdFileTypes.JAVA);
        JBPopupFactory instance = JBPopupFactory.getInstance();
        instance.createComponentPopupBuilder(new JScrollPane(editorTextField), new JBLabel())
                .setTitle("Customer Popup Dialog")
                .setMovable(true)
                .setResizable(true)
                .setMayBeParent(true)
                .setDimensionServiceKey(null, "com.yatoufang.test", true)
                .setNormalWindowLevel(true)
                .setRequestFocus(true)
                .createPopup()
                .showInFocusCenter();
    }
}
