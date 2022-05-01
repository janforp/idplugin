package com.janita.idplugin.common.enums;

import com.janita.idplugin.common.IDatabaseService;
import com.janita.idplugin.common.IHealthService;
import com.janita.idplugin.common.domain.CrQuestionSetting;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CrDataStorageEnum
 *
 * @author zhucj
 * @since 20220324
 */
@AllArgsConstructor
@Getter
public enum CrDataStorageEnum {

    REST_API(true, false, "REST接口") {
        @Override
        public boolean onChange(boolean fromSetting, CrQuestionSetting setting, IDatabaseService database, IHealthService healthService) {
            return healthService.checkHealth(setting);
        }
    },

    SQLITE_DB(true, true, "本地缓存") {
        @Override
        public boolean onChange(boolean fromSetting, CrQuestionSetting setting, IDatabaseService database, IHealthService healthService) {
            return CrDataStorageEnum.doOnChange(fromSetting, setting, database);
        }
    },

    MYSQL_DB(true, false, "MYSQL数据库") {
        @Override
        public boolean onChange(boolean fromSetting, CrQuestionSetting setting, IDatabaseService database, IHealthService healthService) {
            return CrDataStorageEnum.doOnChange(fromSetting, setting, database);
        }
    },
    ;

    private final boolean support;

    private final boolean defaultWay;

    private final String desc;

    public static Set<CrDataStorageEnum> getSupportSet() {
        return Arrays.stream(CrDataStorageEnum.values()).filter(CrDataStorageEnum::isSupport).collect(Collectors.toSet());
    }

    public static CrDataStorageEnum getByDesc(String desc) {
        for (CrDataStorageEnum storageEnum : CrDataStorageEnum.values()) {
            if (storageEnum.getDesc().equals(desc)) {
                return storageEnum;
            }
        }
        return null;
    }

    private static boolean doOnChange(boolean fromSetting, CrQuestionSetting setting, IDatabaseService database) {
        if (fromSetting) {
            new Thread(() -> database.reInitConnect(setting.getDbUrl(), setting.getDbUsername(), setting.getDbPwd())).start();
            return true;
        } else {
            database.reInitConnect(setting.getDbUrl(), setting.getDbUsername(), setting.getDbPwd());
            return database.checkConnectSuccess();
        }
    }

    /**
     * 设置发送变化的时候回调
     *
     * @param fromSetting 从哪里设置
     * @return 成功失败
     */
    public abstract boolean onChange(boolean fromSetting, CrQuestionSetting setting, IDatabaseService service, IHealthService healthService);

    public static CrDataStorageEnum getDefault() {
        for (CrDataStorageEnum storageEnum : CrDataStorageEnum.values()) {
            if (storageEnum.defaultWay) {
                return storageEnum;
            }
        }
        return null;
    }
}