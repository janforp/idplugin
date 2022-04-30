package com.janita.idplugin.dao.crdeveloper.factory;

import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.dao.crdeveloper.ICrDeveloperDAO;
import com.janita.idplugin.dao.crdeveloper.impl.CrDeveloperMysqlDAO;
import com.janita.idplugin.dao.crdeveloper.impl.CrDeveloperRestApiDAO;
import com.janita.idplugin.dao.crdeveloper.impl.CrDeveloperSqliteDAO;

/**
 * CrDeveloperDaoFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class CrDeveloperDaoFactory {

    public static ICrDeveloperDAO getCrDeveloperDAO(CrDataStorageEnum storageEnum) {
        if (storageEnum == CrDataStorageEnum.MYSQL_DB) {
            return CrDeveloperMysqlDAO.getINSTANCE();
        }
        if (storageEnum == CrDataStorageEnum.SQLITE_DB) {
            return CrDeveloperSqliteDAO.getINSTANCE();
        }
        if (storageEnum == CrDataStorageEnum.REST_API) {
            return CrDeveloperRestApiDAO.getINSTANCE();
        }
        throw new RuntimeException("内部异常");
    }
}
