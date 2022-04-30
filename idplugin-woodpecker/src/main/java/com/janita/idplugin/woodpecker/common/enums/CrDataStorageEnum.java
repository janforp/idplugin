package com.janita.idplugin.woodpecker.common.enums;

import com.janita.idplugin.woodpecker.common.util.SingletonBeanFactory;
import com.janita.idplugin.remote.db.IDatabaseService;
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
        public boolean onChange(boolean fromSetting) {
            return SingletonBeanFactory.getCrQuestionRestApiDAO().checkHealth();
        }
    },

    SQLITE_DB(true, true, "本地缓存") {
        @Override
        public boolean onChange(boolean fromSetting) {
            if (fromSetting) {
                new Thread(() -> {
                    IDatabaseService database = SingletonBeanFactory.getSqliteDatabaseServiceImpl();
                    database.reInitConnect();
                }).start();
                return true;
            } else {
                IDatabaseService database = SingletonBeanFactory.getSqliteDatabaseServiceImpl();
                database.reInitConnect();
                return database.checkConnectSuccess();
            }
        }
    },

    MYSQL_DB(true, false, "MYSQL数据库") {
        @Override
        public boolean onChange(boolean fromSetting) {
            if (fromSetting) {
                new Thread(() -> {
                    IDatabaseService database = SingletonBeanFactory.getMySqlDatabaseServiceImpl();
                    database.reInitConnect();
                }).start();
                return true;
            } else {
                IDatabaseService database = SingletonBeanFactory.getMySqlDatabaseServiceImpl();
                database.reInitConnect();
                return database.checkConnectSuccess();
            }
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

    /**
     * 设置发送变化的时候回调
     *
     * @param fromSetting 从哪里设置
     * @return 成功失败
     */
    public abstract boolean onChange(boolean fromSetting);

    /**
     * 检查这种方式当前是否可用
     *
     * @return 当前是否可用
     */
    public boolean checkValidNow() {
        return SingletonBeanFactory.getCrQuestionDAO().checkHealth();
    }

    public static CrDataStorageEnum getDefault() {
        for (CrDataStorageEnum storageEnum : CrDataStorageEnum.values()) {
            if (storageEnum.defaultWay) {
                return storageEnum;
            }
        }
        return null;
    }
}