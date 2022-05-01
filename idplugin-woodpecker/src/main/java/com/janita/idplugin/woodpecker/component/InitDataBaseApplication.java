package com.janita.idplugin.woodpecker.component;

import com.intellij.openapi.components.ApplicationComponent;
import com.janita.idplugin.common.IDatabaseService;
import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.remote.db.factory.DatabaseServiceFactory;
import com.janita.idplugin.woodpecker.setting.CrQuestionSettingUtils;

/**
 * CrQuestionApplication
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("all")
public class InitDataBaseApplication implements ApplicationComponent {

    @Override
    public void initComponent() {
        CrQuestionSetting settingFromCache = CrQuestionSettingUtils.getCrQuestionSettingFromCache();
        CrDataStorageEnum storageWay = settingFromCache.getStorageWay();
        if (storageWay == CrDataStorageEnum.MYSQL_DB || storageWay == CrDataStorageEnum.SQLITE_DB) {
            new Thread(new Task()).start();
        }
    }

    @Override
    public void disposeComponent() {
        CrDataStorageEnum storageEnum = CrQuestionSettingUtils.getStorageWayFromCache();
        IDatabaseService databaseService = DatabaseServiceFactory.getDatabaseService(storageEnum);
        databaseService.closeResource();
        ApplicationComponent.super.disposeComponent();
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            try {
                CrDataStorageEnum storageEnum = CrQuestionSettingUtils.getStorageWayFromCache();
                IDatabaseService databaseService = DatabaseServiceFactory.getDatabaseService(storageEnum);
                CrQuestionSetting setting = CrQuestionSettingUtils.getCrQuestionSettingFromCache();
                databaseService.reInitConnect(setting.getDbUrl(),setting.getDbUsername(),setting.getDbPwd());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}