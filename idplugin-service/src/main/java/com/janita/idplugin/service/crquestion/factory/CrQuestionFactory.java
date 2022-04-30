package com.janita.idplugin.service.crquestion.factory;

import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.service.crquestion.ICrQuestionService;
import com.janita.idplugin.service.crquestion.impl.CrQuestionServiceImpl;

/**
 * CrQuestionFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionFactory {

    public static ICrQuestionService getCrQuestionService(CrDataStorageEnum storageEnum) {
        return CrQuestionServiceImpl.getINSTANCE();
    }
}