package com.janita.idplugin.woodpecker.common.util;

import com.janita.idplugin.woodpecker.common.enums.ButtonType;
import com.janita.idplugin.woodpecker.renderer.CrQuestionTableRenderer;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.MouseEvent;

/**
 * JSwingUtils
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class JSwingUtils {

    public void showErrorDialog(String title, String errorMsg) {
        JOptionPane.showMessageDialog(null, errorMsg, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 列表内容局中
     *
     * @param table 列表
     */
    public void setTableType(JTable table) {
        // 设置表格行宽
        table.setRowHeight(30);
        DefaultTableCellRenderer renderer = new CrQuestionTableRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, renderer);
    }

    public ButtonType getMouseButtonType(MouseEvent mouseEvent) {
        int button = mouseEvent.getButton();
        if (button == MouseEvent.BUTTON1) {
            return ButtonType.LEFT;
        }
        if (button == MouseEvent.BUTTON3) {
            return ButtonType.RIGHT;
        }
        return ButtonType.MIDDLE;
    }
}