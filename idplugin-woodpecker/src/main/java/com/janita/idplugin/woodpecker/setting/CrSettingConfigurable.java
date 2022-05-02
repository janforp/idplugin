package com.janita.idplugin.woodpecker.setting;

import com.intellij.openapi.options.Configurable;
import com.janita.idplugin.common.domain.CrQuestionSetting;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * CrSettingConfigurable
 *
 * @author zhucj
 * @since 20220324
 */
public class CrSettingConfigurable implements Configurable {

    private CrQuestionDataStorageSettingComponent component;

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getDisplayName() {
        return "Woodpecker Setting";
    }

    @Override
    @Nullable
    public JComponent createComponent() {
        CrQuestionDataStorageSettingComponent settingComponent = CrSettingDialogComponentBuilder.createSettingComponent();
        settingComponent.getDbUrlField().setMaximumSize(new Dimension(500, 35));
        settingComponent.getDbUsernameField().setMaximumSize(new Dimension(500, 35));
        settingComponent.getDbPwdField().setMaximumSize(new Dimension(500, 35));
        this.component = settingComponent;
        return settingComponent.getTotalContent();
    }

    @Override
    public boolean isModified() {
        CrQuestionSetting settingFromCache = CrQuestionSettingUtils.getCrQuestionSettingFromCache();
        CrQuestionSetting settingFromInput = CrQuestionSettingUtils.getCrQuestionSettingFromInput(component);
        return !settingFromCache.equals(settingFromInput);
    }

    @Override
    public void apply() {
        CrQuestionSettingUtils.saveFromInput(true, component);
    }

    @Override
    public void reset() {
        Configurable.super.reset();
    }

    @Override
    public void disposeUIResources() {
        this.component = null;
    }
}