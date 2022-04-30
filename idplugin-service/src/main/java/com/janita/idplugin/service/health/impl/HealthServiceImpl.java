package com.janita.idplugin.service.health.impl;

import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.common.IHealthService;
import com.janita.idplugin.dao.crquestion.ICrQuestionDAO;
import com.janita.idplugin.dao.crquestion.factory.CrQuestionDaoFactory;

/**
 * HealthServiceImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class HealthServiceImpl implements IHealthService {

    private static final IHealthService INSTANCE = new HealthServiceImpl();

    public static IHealthService getINSTANCE() {
        return INSTANCE;
    }

    private HealthServiceImpl() {
    }

    @Override
    public boolean checkHealth(CrDataStorageEnum storageEnum, DbConfig config) {
        ICrQuestionDAO crQuestionDAO = CrQuestionDaoFactory.getCrQuestionDAO(storageEnum);
        return crQuestionDAO.checkHealth(storageEnum, config);
    }
}