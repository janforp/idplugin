package com.janita.idplugin.idea.base.util;

import com.janita.idplugin.idea.base.enums.ButtonType;
import lombok.experimental.UtilityClass;

import javax.swing.*;
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