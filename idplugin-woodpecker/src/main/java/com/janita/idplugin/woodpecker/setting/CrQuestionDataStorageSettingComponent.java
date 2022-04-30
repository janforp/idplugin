package com.janita.idplugin.woodpecker.setting;

import lombok.Builder;
import lombok.Getter;

import javax.swing.*;

/**
 * 设置界面
 *
 * @author zhucj
 * @since 20220324
 */
@Builder
@Getter
public class CrQuestionDataStorageSettingComponent {

    private final JComponent totalContent;

    private final JRadioButton localBtn;

    private final JRadioButton mysqlBtn;

    private final JRadioButton restBtn;

    private final JTextField dbUrlField;

    private final JTextField dbUsernameField;

    private final JPasswordField dbPwdField;

    private final JTextField restDomainField;
}