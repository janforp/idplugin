package com.janita.idplugin.service.health.factory;

import com.janita.idplugin.common.IHealthService;
import com.janita.idplugin.service.health.impl.HealthServiceImpl;

/**
 * IHealthServiceFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class HealthServiceFactory {

    public static IHealthService getHealthService() {
        return HealthServiceImpl.getINSTANCE();
    }
}