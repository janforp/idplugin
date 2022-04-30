package com.janita.idplugin.dao.crquestion.factory;

import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.dao.crquestion.ICrQuestionDAO;
import com.janita.idplugin.dao.crquestion.impl.CrQuestionMysqlDAO;
import com.janita.idplugin.dao.crquestion.impl.CrQuestionRestApiDAO;
import com.janita.idplugin.dao.crquestion.impl.CrQuestionSqliteDAO;

/**
 * CrQuestionDaoFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionDaoFactory {

    public static ICrQuestionDAO getCrQuestionDAO(CrDataStorageEnum storageEnum) {
        if (storageEnum == CrDataStorageEnum.MYSQL_DB) {
            return CrQuestionMysqlDAO.getINSTANCE();
        }
        if (storageEnum == CrDataStorageEnum.REST_API) {
            return CrQuestionRestApiDAO.getINSTANCE();
        }
        if (storageEnum == CrDataStorageEnum.SQLITE_DB) {
            return CrQuestionSqliteDAO.getINSTANCE();
        }
        throw new RuntimeException("内部异常");
    }
}
