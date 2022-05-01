package com.janita.idplugin.woodpecker.setting;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;
import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.enums.CrDataStorageEnum;

import javax.swing.*;
import java.awt.*;

/**
 * SettingDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class CrSettingDialogComponentBuilder {

    public static CrQuestionDataStorageSettingComponent createSettingComponent() {
        CrQuestionDataStorageSettingComponent.CrQuestionDataStorageSettingComponentBuilder builder = CrQuestionDataStorageSettingComponent.builder();
        JPanel panel = new JPanel(new BorderLayout());
        builder.totalContent(panel);
        Box topBox = Box.createHorizontalBox();
        JBRadioButton localRb = new JBRadioButton(CrDataStorageEnum.SQLITE_DB.getDesc());
        localRb.setEnabled(CrDataStorageEnum.SQLITE_DB.isSupport());
        localRb.setToolTipText("使用本地缓存，数据无法共享");

        JBRadioButton mysqlRb = new JBRadioButton(CrDataStorageEnum.MYSQL_DB.getDesc());
        mysqlRb.setEnabled(CrDataStorageEnum.MYSQL_DB.isSupport());
        mysqlRb.setToolTipText("使用Mysql数据库,相同数据库数据可以共享");

        JBRadioButton restRb = new JBRadioButton(CrDataStorageEnum.REST_API.getDesc());
        restRb.setEnabled(CrDataStorageEnum.REST_API.isSupport());
        restRb.setToolTipText("自己部署的REST服务，数据可以共享");

        ButtonGroup group = new ButtonGroup();
        group.add(localRb);
        group.add(mysqlRb);
        group.add(restRb);

        topBox.add(restRb);
        topBox.add(localRb);
        topBox.add(mysqlRb);

        builder.restBtn(restRb).mysqlBtn(mysqlRb).localBtn(localRb);

        JPanel card = new JPanel(new CardLayout());

        JPanel local = new JPanel();
        Box mysql = Box.createVerticalBox();
        {
            Box urlPanel = Box.createHorizontalBox();
            urlPanel.add(new JBLabel("数据库地址"));
            JBTextField dbUrlField = new JBTextField(50);
            dbUrlField.setToolTipText("如 jdbc:mysql://127.0.0.1:3306/crhelper");
            urlPanel.add(dbUrlField);

            Box usernamePanel = Box.createHorizontalBox();
            usernamePanel.add(new JBLabel("数据库用户"));
            JBTextField dbUsernameField = new JBTextField(50);
            usernamePanel.add(dbUsernameField);

            Box pwdPanel = Box.createHorizontalBox();
            pwdPanel.add(new JBLabel("数据库密码"));
            JBPasswordField dbPwdField = new JBPasswordField();
            pwdPanel.add(dbPwdField);

            mysql.add(pwdPanel, FlowLayout.LEFT);
            mysql.add(usernamePanel, FlowLayout.LEFT);
            mysql.add(urlPanel, FlowLayout.LEFT);

            builder.dbUrlField(dbUrlField).dbUsernameField(dbUsernameField).dbPwdField(dbPwdField);
        }

        JPanel rest = new JPanel();
        {
            Box restPanel = Box.createHorizontalBox();
            restPanel.add(new JLabel("域名："));
            JBTextField domainField = new JBTextField(50);
            domainField.setToolTipText("如 http://127.0.0.1:8080");
            restPanel.add(domainField);

            rest.add(restPanel);
            builder.restDomainField(domainField);

        }

        card.add(local, "local");
        card.add(mysql, "mysql");
        card.add(rest, "rest");

        CardLayout layout = (CardLayout) card.getLayout();
        layout.show(card, "mysql");

        panel.add(topBox, BorderLayout.NORTH);
        panel.add(card);
        CrQuestionDataStorageSettingComponent component = builder.build();
        addActionListener(component, layout, card);
        selectRadix(component);
        initFieldText(component);
        return component;
    }

    private static void initFieldText(CrQuestionDataStorageSettingComponent component) {
        CrQuestionSetting setting = CrQuestionSettingUtils.getCrQuestionSettingFromCache();
        component.getDbUrlField().setText(setting.getDbUrl());
        component.getDbUsernameField().setText(setting.getDbUsername());
        component.getDbPwdField().setText(setting.getDbPwd());
        component.getRestDomainField().setText(setting.getRestApiDomain());
    }

    private static void selectRadix(CrQuestionDataStorageSettingComponent component) {
        CrDataStorageEnum storageEnum = CrQuestionSettingUtils.getStorageWayFromCache();
        if (storageEnum == null) {
            // 默认
            storageEnum = CrDataStorageEnum.getDefault();
        }
        if (storageEnum == CrDataStorageEnum.SQLITE_DB) {
            component.getLocalBtn().setSelected(true);
            return;
        }
        if (storageEnum == CrDataStorageEnum.MYSQL_DB) {
            component.getMysqlBtn().setSelected(true);
            return;
        }
        if (storageEnum == CrDataStorageEnum.REST_API) {
            component.getRestBtn().setSelected(true);
            return;
        }

        component.getLocalBtn().setSelected(true);
    }

    private static void addActionListener(CrQuestionDataStorageSettingComponent component, CardLayout layout, JPanel card) {
        component.getLocalBtn().addChangeListener(e -> {
            if (component.getLocalBtn().isSelected()) {
                layout.show(card, "local");
            }
        });
        component.getMysqlBtn().addChangeListener(e -> {
            if (component.getMysqlBtn().isSelected()) {
                layout.show(card, "mysql");
            }
        });
        component.getRestBtn().addChangeListener(e -> {
            if (component.getRestBtn().isSelected()) {
                layout.show(card, "rest");
            }
        });
    }
}