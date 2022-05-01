package com.janita.idplugin.woodpecker.renderer;

import com.intellij.ui.JBColor;
import com.janita.idplugin.common.enums.CrQuestionState;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * CrQuestionTableRednderer
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionTableRenderer extends DefaultTableCellRenderer {

    private final JLabel stateLabel = new JLabel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            // 如果不是第五列表，则使用默认的
            return super.getTableCellRendererComponent(table, value, true, hasFocus, row, column);
        }
        boolean stringType = value instanceof String;
        if (!stringType) {
            return super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
        }

        String text = (String) value;
        stateLabel.setText(text);
        stateLabel.setHorizontalAlignment(CENTER);
        // 因为这个对象是复用的，所以每次都要初始化成默认都颜色，否则就会使用上一次设置都颜色
        stateLabel.setForeground(JBColor.BLACK);

        if (column == 8) {
            CrQuestionState state = CrQuestionState.getByDescOrReturnNull(text);
            if (CrQuestionState.UNSOLVED == state) {
                stateLabel.setForeground(JBColor.RED);
            }
            if (CrQuestionState.SOLVED == state) {
                stateLabel.setForeground(JBColor.GREEN);
            }
            if (CrQuestionState.REJECT == state) {
                stateLabel.setForeground(JBColor.BLUE);
            }
            if (CrQuestionState.DUPLICATE == state) {
                stateLabel.setForeground(JBColor.GRAY);
            }
            if (CrQuestionState.CLOSED == state) {
                stateLabel.setForeground(JBColor.GRAY);
            }
            return stateLabel;
        }

        if (column == 4) {
            if ("阻断".equals(text) || "严重".equals(text)) {
                stateLabel.setForeground(JBColor.RED);
            }
            return stateLabel;
        }
        if (column == 3) {
            if ("BUG".equals(text)) {
                stateLabel.setForeground(JBColor.RED);
            }
            return stateLabel;
        }
        return super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
    }
}
