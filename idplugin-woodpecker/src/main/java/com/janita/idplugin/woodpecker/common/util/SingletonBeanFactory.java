package com.janita.idplugin.woodpecker.common.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.ApplicationManager;
import com.janita.idplugin.woodpecker.dao.developer.ICrDeveloperDAO;
import com.janita.idplugin.woodpecker.db.impl.MySqlDatabaseServiceImpl;
import com.janita.idplugin.woodpecker.service.CrQuestionService;
import com.janita.idplugin.woodpecker.common.enums.CrDataStorageEnum;
import com.janita.idplugin.woodpecker.dao.developer.impl.CrDeveloperMysqlDAO;
import com.janita.idplugin.woodpecker.dao.developer.impl.CrDeveloperRestApiDAO;
import com.janita.idplugin.woodpecker.dao.developer.impl.CrDeveloperSqliteDAO;
import com.janita.idplugin.woodpecker.dao.question.ICrQuestionDAO;
import com.janita.idplugin.woodpecker.dao.question.impl.CrQuestionMySqlDAO;
import com.janita.idplugin.woodpecker.dao.question.impl.CrQuestionRestApiDAO;
import com.janita.idplugin.woodpecker.dao.question.impl.CrQuestionSqliteDAO;
import com.janita.idplugin.remote.db.IDatabaseService;
import com.janita.idplugin.woodpecker.db.impl.SqliteDatabaseServiceImpl;
import com.janita.idplugin.woodpecker.setting.CrQuestionSetting;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class SingletonBeanFactory {

    private static final Map<String, Set<Object>> BEAN_MAP = new ConcurrentHashMap<>();

    public IDatabaseService getMySqlDatabaseServiceImpl() {
        IDatabaseService service = ApplicationManager.getApplication().getService(MySqlDatabaseServiceImpl.class);
        doAfterGetBean("MySqlDatabaseServiceImpl", service);
        return service;
    }

    public static IDatabaseService getSqliteDatabaseServiceImpl() {
        IDatabaseService service = ApplicationManager.getApplication().getService(SqliteDatabaseServiceImpl.class);
        doAfterGetBean("SqliteDatabaseServiceImpl", service);
        return service;
    }

    public static IDatabaseService getDatabaseService() {
        CrDataStorageEnum storageWay = CrQuestionSetting.getStorageWayFromCache();
        if (storageWay == CrDataStorageEnum.MYSQL_DB) {
            return SingletonBeanFactory.getMySqlDatabaseServiceImpl();
        }
        return SingletonBeanFactory.getSqliteDatabaseServiceImpl();
    }

    public static CrQuestionService getCrQuestionService() {
        CrQuestionService service = ApplicationManager.getApplication().getService(CrQuestionService.class);
        doAfterGetBean("CrQuestionService", service);
        return service;
    }

    public static PropertiesComponent getPropertiesComponent() {
        PropertiesComponent component = PropertiesComponent.getInstance();
        doAfterGetBean("PropertiesComponent", component);
        return component;
    }

    public static ICrQuestionDAO getCrQuestionMySqlDAO() {
        ICrQuestionDAO service = ApplicationManager.getApplication().getService(CrQuestionMySqlDAO.class);
        doAfterGetBean("CrQuestionMySqlDAO", service);
        return service;
    }

    public static ICrQuestionDAO getCrQuestionSqliteDAO() {
        ICrQuestionDAO service = ApplicationManager.getApplication().getService(CrQuestionSqliteDAO.class);
        doAfterGetBean("CrQuestionSqliteDAO", service);
        return service;
    }

    public static ICrQuestionDAO getCrQuestionRestApiDAO() {
        ICrQuestionDAO service = ApplicationManager.getApplication().getService(CrQuestionRestApiDAO.class);
        doAfterGetBean("CrQuestionRestApiDAO", service);
        return service;
    }

    public static ICrQuestionDAO getCrQuestionDAO() {
        CrDataStorageEnum storageWayEnum = CrQuestionSetting.getStorageWayFromCache();
        if (storageWayEnum == CrDataStorageEnum.REST_API) {
            return SingletonBeanFactory.getCrQuestionRestApiDAO();
        }
        if (storageWayEnum == CrDataStorageEnum.MYSQL_DB) {
            return SingletonBeanFactory.getCrQuestionMySqlDAO();
        }
        return SingletonBeanFactory.getCrQuestionSqliteDAO();
    }

    public static ICrDeveloperDAO getCrDeveloperDAO() {
        CrDataStorageEnum storageWayEnum = CrQuestionSetting.getStorageWayFromCache();
        if (storageWayEnum == CrDataStorageEnum.SQLITE_DB) {
            return getCrDeveloperSqliteDAO();
        }
        if (storageWayEnum == CrDataStorageEnum.MYSQL_DB) {
            return getCrDeveloperMysqlDAO();
        }
        return SingletonBeanFactory.getCrDeveloperRestApiDAO();
    }

    private static ICrDeveloperDAO getCrDeveloperRestApiDAO() {
        ICrDeveloperDAO service = ApplicationManager.getApplication().getService(CrDeveloperRestApiDAO.class);
        doAfterGetBean("CrDeveloperRestApiDAO", service);
        return service;
    }

    private static ICrDeveloperDAO getCrDeveloperSqliteDAO() {
        ICrDeveloperDAO service = ApplicationManager.getApplication().getService(CrDeveloperSqliteDAO.class);
        doAfterGetBean("CrDeveloperSqliteDAO", service);
        return service;
    }

    private static ICrDeveloperDAO getCrDeveloperMysqlDAO() {
        ICrDeveloperDAO service = ApplicationManager.getApplication().getService(CrDeveloperMysqlDAO.class);
        doAfterGetBean("CrDeveloperMysqlDAO", service);
        return service;
    }

    @SuppressWarnings("all")
    private static void doAfterGetBean(String beanName, Object bean) {
        Set<Object> objectSet = BEAN_MAP.get(beanName);
        if (objectSet == null) {
            objectSet = new HashSet<>();
            BEAN_MAP.put(beanName, objectSet);
        }
        objectSet.add(bean);
    }
}
