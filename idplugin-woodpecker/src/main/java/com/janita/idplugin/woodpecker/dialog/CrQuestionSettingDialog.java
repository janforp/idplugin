package com.janita.idplugin.woodpecker.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.janita.idplugin.woodpecker.setting.CrQuestionDataStorageSettingComponent;
import com.janita.idplugin.woodpecker.setting.CrQuestionSettingUtils;
import com.janita.idplugin.woodpecker.setting.CrSettingBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * CrQuestionSettingDialog
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionSettingDialog extends DialogWrapper {

    private CrQuestionDataStorageSettingComponent component;

    public CrQuestionSettingDialog() {
        super(true);
        init();
    }

    @Override
    @Nullable
    protected JComponent createCenterPanel() {
        CrQuestionDataStorageSettingComponent component = CrSettingBuilder.createSettingComponent();
        this.component = component;
        return component.getTotalContent();
    }

    @Override
    public void doOKAction() {
        saveWhenPressOk();
        super.doOKAction();
    }

    private void saveWhenPressOk() {
        ValidationInfo validationInfo = doValidate();
        if (validationInfo == null) {
            CrQuestionSettingUtils.saveFromInput(false, component);
        }
    }

    @Override
    @Nullable
    protected ValidationInfo doValidate() {
        // 还要确保输入的配置正确
        if (component.getLocalBtn().isSelected()) {
            return null;
        }
        if (component.getMysqlBtn().isSelected()) {
            JTextField dbUrlField = component.getDbUrlField();
            JTextField dbUsernameField = component.getDbUsernameField();
            JPasswordField dbPwdField = component.getDbPwdField();

            String url = dbUrlField.getText().trim();
            if (url.length() == 0) {
                return new ValidationInfo("请输入正确的数据库地址");
            }
            String username = dbUsernameField.getText().trim();
            if (username.length() == 0) {
                return new ValidationInfo("请输入正确的数据库用户名称");
            }
            char[] password = dbPwdField.getPassword();
            if (password == null || password.length == 0) {
                return new ValidationInfo("请输入正确的数据库密码");
            }
            return null;
        }
        if (component.getRestBtn().isSelected()) {
            JTextField restDomainField = component.getRestDomainField();
            String restDomain = restDomainField.getText().trim();
            if (restDomain.length() == 0) {
                return new ValidationInfo("请输入正确的接口域名");
            }
        }
        boolean success = CrQuestionSettingUtils.saveFromInput(false, component);
        if (!success) {
            return new ValidationInfo("健康检查失败，请确认输入是否正确！");
        }
        return null;
    }

    public static boolean checkStorageAndReturnIfClickOk() {
        boolean valid = CrQuestionSettingUtils.checkValidNow();
        if (valid) {
            return true;
        }
        CrQuestionSettingDialog dialog = new CrQuestionSettingDialog();
        if (dialog.showAndGet()) {
            dialog.doOKAction();
            return true;
        }
        return false;
    }
}