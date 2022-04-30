package com.janita.idplugin.service.crdeveloper.factory;

import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.service.crdeveloper.ICrDeveloperService;
import com.janita.idplugin.service.crdeveloper.impl.CrDeveloperServiceImpl;

/**
 * CrDeveloperServiceFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class CrDeveloperServiceFactory {

    public static ICrDeveloperService getICrDeveloperService(CrDataStorageEnum storageEnum) {
        return CrDeveloperServiceImpl.getINSTANCE();
    }
}
