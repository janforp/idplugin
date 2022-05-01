package com.janita.idplugin.dao.crquestion.impl;

import com.alibaba.fastjson.JSONObject;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.CrRestApiEnum;
import com.janita.idplugin.common.request.CrQuestionQueryRequest;
import com.janita.idplugin.dao.crquestion.ICrQuestionDAO;
import com.janita.idplugin.remote.rest.HttpUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * CrQuestionRestApiDAO
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionRestApiDAO implements ICrQuestionDAO {
    private static final ICrQuestionDAO INSTANCE = new CrQuestionRestApiDAO();

    public static ICrQuestionDAO getINSTANCE() {
        return INSTANCE;
    }
    private CrQuestionRestApiDAO() {
    }

    @Override
    public boolean checkHealth(CrQuestionSetting setting) {
        try {
            return HttpUtils.get(setting, CrRestApiEnum.HEALTH);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(CrQuestionSetting setting, CrQuestion question) {
        Object id = HttpUtils.post(setting,CrRestApiEnum.QUESTION_ADD, question);
        Long longId = Long.valueOf(id.toString());
        question.setId(longId);
        return true;
    }

    @Override
    public boolean update(CrQuestionSetting setting, CrQuestion question) {
        HttpUtils.post(setting,CrRestApiEnum.QUESTION_UPDATE, question);
        return true;
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> queryQuestion(CrQuestionSetting setting, CrQuestionQueryRequest request) {
        List<?> list = HttpUtils.post(setting,CrRestApiEnum.QUESTION_QUERY, request);
        if (CollectionUtils.isEmpty(list)) {
            return Pair.of(true, new ArrayList<>(0));
        }
        List<CrQuestion> questionList = new ArrayList<>();
        for (Object obj : list) {
            LinkedHashMap map = (LinkedHashMap) obj;
            CrQuestion question = JSONObject.parseObject(JSONObject.toJSONString(map), CrQuestion.class);
            questionList.add(question);
        }
        return Pair.of(true, questionList);
    }
}