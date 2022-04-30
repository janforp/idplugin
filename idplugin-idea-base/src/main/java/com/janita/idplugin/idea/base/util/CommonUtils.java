package com.janita.idplugin.idea.base.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.janita.idplugin.idea.base.domain.SelectFileInfo;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

/**
 * CommonUtils
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
@SuppressWarnings("unused")
public class CommonUtils {

    public static SelectFileInfo getSelectFileInfo(AnActionEvent e) {
        PsiFile psiFile = e.getRequiredData(CommonDataKeys.PSI_FILE);
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        int offsetStart = selectionModel.getSelectionStart();
        int offsetEnd = selectionModel.getSelectionEnd();
        // 虚拟文件
        VirtualFile virtualFile = psiFile.getVirtualFile();
        // 获取文件类型,而非后缀
        String language = psiFile.getLanguage().getDisplayName().toLowerCase();
        // 获取文件的路径
        String filePath = virtualFile.getPath();
        String fileName = virtualFile.getName();

        return SelectFileInfo.builder()
                .language(language)
                .filePath(filePath)
                .fileName(fileName)
                .selectedText(selectedText)
                .offsetStart(offsetStart)
                .offsetEnd(offsetEnd)
                .build();
    }

    public static String getSelectedText(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        // 用户选择的文本
        return selectionModel.getSelectedText();
    }

    public static String getClassName(AnActionEvent e) {
        // 当前文件的名称
        return e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
    }

    public static void setToClipboard(String text) {
        // 拿到剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(text);
        clipboard.setContents(stringSelection, null);
    }

    public static JMenuItem buildJMenuItem(String title, String icon, ActionListener listener) {
        JMenuItem item = new JMenuItem();
        item.setText(title);
        ImageIcon imageIcon = getImageIcon(icon, 20, 20);
        item.setIcon(imageIcon);
        item.addActionListener(listener);
        return item;
    }

    public static void clearDefaultTableModel(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    public static JPopupMenu buildJPopupMenu(JMenuItem... items) {
        JPopupMenu menu = new JPopupMenu();
        if (items == null || items.length == 0) {
            return menu;
        }
        for (JMenuItem item : items) {
            menu.add(item);
        }
        return menu;
    }

    public static ImageIcon getImageIcon(String path, int width, int height) {
        URL resource = CommonUtils.class.getResource(path);
        if (resource == null) {
            return new ImageIcon();
        }
        if (width == 0 || height == 0) {
            return new ImageIcon(resource);
        }
        ImageIcon icon = new ImageIcon(resource);
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return icon;
    }

    public static boolean hasSelectAnyText(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        return editor.getSelectionModel().hasSelection();
    }

    public static void showNotification(String content, MessageType messageType) {
        NotificationGroup notificationGroup = new NotificationGroup("Code Review", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(content, messageType);
        notification.setTitle("Code review");
        Notifications.Bus.notify(notification);
    }

    public static VirtualFile getByPathThenName(Project project, String filePath, String fileName) {
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(filePath);
        if (virtualFile == null) {
            virtualFile = JarFileSystem.getInstance().findFileByPath(filePath);
        }
        if (virtualFile == null) {
            PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.allScope(project));
            if (psiFiles.length != 0) {
                PsiFile psiFile = psiFiles[0];
                virtualFile = psiFile.getVirtualFile();
            }
        }
        return virtualFile;
    }

    /**
     * 根据文件名称，打开该文件
     */
    public static void openFileAndLocationToText(Project project, String filePath, String fileName, Integer offsetStart, Integer offsetEnd, String locationText) {
        VirtualFile virtualFile = getByPathThenName(project, filePath, fileName);
        if (virtualFile == null) {
            return;
        }
        FileEditor[] fileEditors = FileEditorManager.getInstance(project).openFile(virtualFile, true, true);
        if (fileEditors.length == 0) {
            return;
        }
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) {
            return;
        }
        Document document = editor.getDocument();
        int maxLen = document.getTextLength();
        String textInScope = document.getText(new TextRange(offsetStart, offsetEnd));
        boolean contentEquals = Objects.equals(locationText, textInScope);
        if (!contentEquals) {
            String trim = locationText.trim();
            int index = document.getText().indexOf(trim);
            if (index >= 0) {
                int space = offsetEnd - offsetStart;
                offsetStart = index;
                offsetEnd = index + (space > 0 ? trim.length() : -trim.length());
                contentEquals = true;
            }
        }
        if (contentEquals && offsetStart < maxLen && offsetEnd <= maxLen) {
            editor.getCaretModel().moveToOffset(offsetStart, true);
            editor.getSelectionModel().setSelection(offsetStart, offsetEnd);
            editor.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
        }
    }

    public static Project getProject() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        Project activeProject = null;
        for (Project project : projects) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                activeProject = project;
            }
        }
        return activeProject;
    }

    public static void openFile(Project project, String fullPath) {
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(fullPath);
        if (virtualFile == null) {
            return;
        }
        FileEditor[] fileEditors = FileEditorManager.getInstance(project).openFile(virtualFile, true, true);
        if (fileEditors.length == 0) {
            return;
        }
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) {
            return;
        }
        OpenFileDescriptor descriptor = new OpenFileDescriptor(project, virtualFile);
        descriptor.navigate(true);
    }
}