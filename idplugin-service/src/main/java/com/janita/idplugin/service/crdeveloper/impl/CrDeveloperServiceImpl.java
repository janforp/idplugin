package com.janita.idplugin.service.crdeveloper.impl;

import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.common.request.CrDeveloperQueryRequest;
import com.janita.idplugin.common.request.CrDeveloperSaveRequest;
import com.janita.idplugin.dao.crdeveloper.ICrDeveloperDAO;
import com.janita.idplugin.dao.crdeveloper.factory.CrDeveloperDaoFactory;
import com.janita.idplugin.service.crdeveloper.ICrDeveloperService;

import java.util.List;

/**
 * CrDeveloperImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class CrDeveloperServiceImpl implements ICrDeveloperService {

    private static final ICrDeveloperService INSTANCE = new CrDeveloperServiceImpl();

    public static ICrDeveloperService getINSTANCE() {
        return INSTANCE;
    }

    private CrDeveloperServiceImpl() {
    }

    @Override
    public void save(CrDataStorageEnum storageEnum, DbConfig config, CrDeveloperSaveRequest request) {
        ICrDeveloperDAO crDeveloperDAO = CrDeveloperDaoFactory.getCrDeveloperDAO(storageEnum);
        crDeveloperDAO.save(storageEnum, config, request);
    }

    @Override
    public List<CrDeveloper> query(CrDataStorageEnum storageEnum, DbConfig dbConfig, CrDeveloperQueryRequest request) {
        ICrDeveloperDAO crDeveloperDAO = CrDeveloperDaoFactory.getCrDeveloperDAO(storageEnum);
        Pair<Boolean, List<CrDeveloper>> booleanListPair = crDeveloperDAO.queryDeveloper(storageEnum, dbConfig, request);
        return booleanListPair.getRight();
    }
}
