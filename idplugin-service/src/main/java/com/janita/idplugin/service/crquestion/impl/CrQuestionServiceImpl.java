package com.janita.idplugin.service.crquestion.impl;

import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.common.enums.OperationType;
import com.janita.idplugin.common.request.CrQuestionQueryRequest;
import com.janita.idplugin.dao.crquestion.ICrQuestionDAO;
import com.janita.idplugin.dao.crquestion.factory.CrQuestionDaoFactory;
import com.janita.idplugin.service.crquestion.ICrQuestionService;
import com.janita.idplugin.service.crquestion.domain.CrQuestionSaveRequest;
import com.janita.idplugin.service.wechat.IWeChatService;
import com.janita.idplugin.service.wechat.factory.WeChatServiceFactory;

import java.util.List;

/**
 * CrQuestionServiceImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionServiceImpl implements ICrQuestionService {

    private CrQuestionServiceImpl() {
    }

    private static final ICrQuestionService INSTANCE = new CrQuestionServiceImpl();

    public static ICrQuestionService getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void save(CrDataStorageEnum storageEnum, DbConfig config, CrQuestionSaveRequest create) {
        CrQuestion question = create.getQuestion();
        boolean isAdd = question.getId() == null;
        ICrQuestionDAO crQuestionDAO = CrQuestionDaoFactory.getCrQuestionDAO(storageEnum);
        boolean success;
        if (isAdd) {
            success = crQuestionDAO.insert(storageEnum, config, question);
        } else {
            success = crQuestionDAO.update(storageEnum, config, question);
        }
        if (!success) {
            return;
        }
        if (!create.isSendMsg()) {
            return;
        }
        IWeChatService weChatService = WeChatServiceFactory.getWeChatService();
        weChatService.sendByMarkDown(question, create.getPhoneList(), isAdd ? OperationType.add : OperationType.update);
    }

    @Override
    public List<CrQuestion> query(CrDataStorageEnum storageEnum, DbConfig dbConfig, CrQuestionQueryRequest request) {
        ICrQuestionDAO crQuestionDAO = CrQuestionDaoFactory.getCrQuestionDAO(storageEnum);
        Pair<Boolean, List<CrQuestion>> booleanListPair = crQuestionDAO.queryQuestion(storageEnum, dbConfig, request);
        return booleanListPair.getRight();
    }
}