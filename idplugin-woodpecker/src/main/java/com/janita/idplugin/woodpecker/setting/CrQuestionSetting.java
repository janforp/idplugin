package com.janita.idplugin.woodpecker.setting;

import com.janita.idplugin.common.constant.PersistentKeys;
import com.janita.idplugin.woodpecker.common.enums.CrDataStorageEnum;
import com.janita.idplugin.woodpecker.common.util.SingletonBeanFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * CrQuestionSetting
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@EqualsAndHashCode
public class CrQuestionSetting {

    private CrDataStorageEnum storageWay;

    private String dbUrl;

    private String dbUsername;

    private String dbPwd;

    private String restApiDomain;

    public static boolean saveFromInput(boolean fromSetting, CrQuestionDataStorageSettingComponent component) {
        CrQuestionSetting input = getCrQuestionSettingFromInput(component);
        CrQuestionSetting cache = getCrQuestionSettingFromCache();
        boolean configChange = !cache.equals(input);
        if (!configChange && fromSetting) {
            // setting页面配置没有发送变化，则直接返回
            return true;
        }
        // 弹框页面，没有发生变化也要健康检查，因为可能之前就是不对的！
        if (configChange) {
            SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.CrDataStorageConfig.CR_DATA_STORAGE_WAY, input.getStorageWay().getDesc());
            SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.CrDataStorageConfig.MYSQL_URL, input.getDbUrl());
            SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.CrDataStorageConfig.MYSQL_USERNAME, input.getDbUsername());
            SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.CrDataStorageConfig.MYSQL_PWD, input.getDbPwd());
            SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.CrDataStorageConfig.REST_API_DOMAIN, input.getRestApiDomain());
        }
        return input.getStorageWay().onChange(fromSetting);
    }

    public static CrQuestionSetting getCrQuestionSettingFromCache() {
        String way = SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.CrDataStorageConfig.CR_DATA_STORAGE_WAY);
        CrDataStorageEnum storageEnum = CrDataStorageEnum.getByDesc(way);

        CrQuestionSetting setting = new CrQuestionSetting();
        setting.setStorageWay(storageEnum);
        setting.setDbUrl(SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.CrDataStorageConfig.MYSQL_URL));
        setting.setDbUsername(SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.CrDataStorageConfig.MYSQL_USERNAME));
        setting.setDbPwd(SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.CrDataStorageConfig.MYSQL_PWD));
        setting.setRestApiDomain(SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.CrDataStorageConfig.REST_API_DOMAIN));
        return setting;
    }

    public static CrDataStorageEnum getStorageWayFromCache() {
        CrQuestionSetting setting = getCrQuestionSettingFromCache();
        return setting.getStorageWay();
    }

    public static CrQuestionSetting getCrQuestionSettingFromInput(CrQuestionDataStorageSettingComponent component) {
        CrDataStorageEnum storageWay = getSelectedStorageWay(component);
        CrQuestionSetting setting = new CrQuestionSetting();
        setting.setStorageWay(storageWay);
        setting.setDbUrl(component.getDbUrlField().getText());
        setting.setDbUsername(component.getDbUsernameField().getText());
        setting.setDbPwd(new String(component.getDbPwdField().getPassword()));
        setting.setRestApiDomain(component.getRestDomainField().getText());
        return setting;
    }

    public static CrDataStorageEnum getSelectedStorageWay(CrQuestionDataStorageSettingComponent component) {
        if (component.getLocalBtn().isSelected()) {
            return CrDataStorageEnum.SQLITE_DB;
        }
        if (component.getMysqlBtn().isSelected()) {
            return CrDataStorageEnum.MYSQL_DB;
        }
        if (component.getRestBtn().isSelected()) {
            return CrDataStorageEnum.REST_API;
        }
        return CrDataStorageEnum.SQLITE_DB;
    }

    public static boolean checkValidNow() {
        CrDataStorageEnum storageEnum = getStorageWayFromCache();
        if (storageEnum == null) {
            return false;
        }
        Set<CrDataStorageEnum> supportSet = CrDataStorageEnum.getSupportSet();
        if (!supportSet.contains(storageEnum)) {
            // 如果之前设置的方式已经不支持了，则再次弹出
            return false;
        }
        return storageEnum.checkValidNow();
    }
}