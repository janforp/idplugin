package com.janita.idplugin.remote.db.factory;

import com.janita.idplugin.common.IDatabaseService;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.remote.db.impl.MySqlDatabaseServiceImpl;
import com.janita.idplugin.remote.db.impl.SqliteDatabaseServiceImpl;

/**
 * DatabaseServiceFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class DatabaseServiceFactory {

    public static IDatabaseService getDatabaseService(CrDataStorageEnum storageEnum) {
        if (storageEnum == CrDataStorageEnum.SQLITE_DB) {
            return SqliteDatabaseServiceImpl.getINSTANCE();
        }
        if (storageEnum == CrDataStorageEnum.MYSQL_DB) {
            return MySqlDatabaseServiceImpl.getINSTANCE();
        }
        throw new RuntimeException("内部异常");
    }
}
