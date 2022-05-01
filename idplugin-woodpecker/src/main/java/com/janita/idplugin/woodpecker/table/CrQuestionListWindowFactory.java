package com.janita.idplugin.woodpecker.table;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowType;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionListWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        CrQuestionListWindow window = new CrQuestionListWindow(project, toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent((JComponent) window.getContentPane(), "", false);
        toolWindow.setDefaultState(ToolWindowAnchor.RIGHT, ToolWindowType.WINDOWED, null);
        toolWindow.getContentManager().addContent(content);
    }
}