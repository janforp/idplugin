package com.janita.idplugin.woodpecker.component;

import com.intellij.openapi.components.ApplicationComponent;
import com.janita.idplugin.woodpecker.common.enums.CrDataStorageEnum;
import com.janita.idplugin.woodpecker.common.util.SingletonBeanFactory;
import com.janita.idplugin.remote.db.IDatabaseService;
import com.janita.idplugin.woodpecker.setting.CrQuestionSetting;

/**
 * CrQuestionApplication
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("all")
public class CrQuestionApplication implements ApplicationComponent {

    @Override
    public void initComponent() {
        CrQuestionSetting settingFromCache = CrQuestionSetting.getCrQuestionSettingFromCache();
        CrDataStorageEnum storageWay = settingFromCache.getStorageWay();
        if (storageWay == CrDataStorageEnum.MYSQL_DB || storageWay == CrDataStorageEnum.SQLITE_DB) {
            new Thread(new Task()).start();
        }
    }

    @Override
    public void disposeComponent() {
        SingletonBeanFactory.getDatabaseService().closeResource();
        ApplicationComponent.super.disposeComponent();
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            try {
                IDatabaseService database = SingletonBeanFactory.getDatabaseService();
                CrQuestionSetting setting = CrQuestionSetting.getCrQuestionSettingFromCache();
                database.reInitConnect(setting.getDbUrl(),setting.getDbUsername(),setting.getDbPwd());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
