package com.janita.idplugin.service.health.impl;

import com.janita.idplugin.common.IHealthService;
import com.janita.idplugin.common.domain.CrQuestionSetting;
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
    public boolean checkHealth(CrQuestionSetting setting) {
        ICrQuestionDAO crQuestionDAO = CrQuestionDaoFactory.getCrQuestionDAO(setting.getStorageWay());
        return crQuestionDAO.checkHealth(setting);
    }
}