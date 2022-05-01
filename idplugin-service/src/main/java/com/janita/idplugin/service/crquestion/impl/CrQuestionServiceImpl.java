package com.janita.idplugin.service.crquestion.impl;

import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.domain.Pair;
import com.janita.idplugin.common.entity.CrQuestion;
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
    public void save(CrQuestionSetting setting, CrQuestionSaveRequest request) {
        CrQuestion question = request.getQuestion();
        boolean isAdd = question.getId() == null;
        ICrQuestionDAO crQuestionDAO = CrQuestionDaoFactory.getCrQuestionDAO(setting.getStorageWay());
        boolean success;
        if (isAdd) {
            success = crQuestionDAO.insert(setting, question);
        } else {
            success = crQuestionDAO.update(setting, question);
        }
        if (!success) {
            return;
        }
        if (!request.isSendMsg()) {
            return;
        }
        IWeChatService weChatService = WeChatServiceFactory.getWeChatService();
        weChatService.sendByMarkDown(question, request.getPhoneList(), isAdd ? OperationType.add : OperationType.update);
    }

    @Override
    public List<CrQuestion> query(CrQuestionSetting setting, CrQuestionQueryRequest request) {
        ICrQuestionDAO crQuestionDAO = CrQuestionDaoFactory.getCrQuestionDAO(setting.getStorageWay());
        Pair<Boolean, List<CrQuestion>> booleanListPair = crQuestionDAO.queryQuestion(setting, request);
        return booleanListPair.getRight();
    }
}