package com.janita.idplugin.dao.crquestion.impl;

import com.alibaba.fastjson.JSONObject;
import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.common.enums.CrRestApiEnum;
import com.janita.idplugin.common.exception.PluginRuntimeException;
import com.janita.idplugin.common.request.CrQuestionQueryRequest;
import com.janita.idplugin.dao.crquestion.ICrQuestionDAO;
import com.janita.idplugin.remote.api.ApiResponse;
import com.janita.idplugin.remote.api.Head;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.remote.rest.RestTemplateFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * CrQuestionRestApiDAO
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CrQuestionRestApiDAO implements ICrQuestionDAO {
    private static final ICrQuestionDAO INSTANCE = new CrQuestionRestApiDAO();

    public static ICrQuestionDAO getINSTANCE() {
        return INSTANCE;
    }
    private CrQuestionRestApiDAO() {
    }

    @Override
    public boolean checkHealth(CrDataStorageEnum storageEnum,DbConfig config) {
        try {
            return get(config, CrRestApiEnum.HEALTH);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(CrDataStorageEnum storageEnum,DbConfig config, CrQuestion question) {
        Object id = post(config,CrRestApiEnum.QUESTION_ADD, question);
        Long longId = Long.valueOf(id.toString());
        question.setId(longId);
        return true;
    }

    @Override
    public boolean update(CrDataStorageEnum storageEnum,DbConfig config, CrQuestion question) {
        post(config,CrRestApiEnum.QUESTION_UPDATE, question);
        return true;
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> queryQuestion(CrDataStorageEnum storageEnum,DbConfig config, CrQuestionQueryRequest request) {
        List list = post(config,CrRestApiEnum.QUESTION_QUERY, request);
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

    public static <T> T get(DbConfig config,CrRestApiEnum apiEnum) {
        String restApiDomain = config.getDomain();
        ResponseEntity<ApiResponse> responseEntity = RestTemplateFactory.getRestTemplate().getForEntity(apiEnum.getUrl(restApiDomain), ApiResponse.class);
        return (T) checkAndReturnEntity(responseEntity);
    }

    public static Object checkAndReturnEntity(ResponseEntity<ApiResponse> entity) {
        if (entity.getStatusCode() != HttpStatus.valueOf(200)) {
            throw new PluginRuntimeException("调用异常");
        }
        ApiResponse response = entity.getBody();
        boolean success = success(response);
        if (!success) {
            throw new PluginRuntimeException(response.getHead().getMsg());
        }
        return entity.getBody().getData();
    }

    public static <T> T post(DbConfig config,CrRestApiEnum apiEnum, Object requestBody) {
        String restApiDomain = config.getDomain();
        ResponseEntity<ApiResponse> postForEntity = RestTemplateFactory.getRestTemplate().postForEntity(apiEnum.getUrl(restApiDomain), requestBody, ApiResponse.class);
        return (T) checkAndReturnEntity(postForEntity);
    }

    public static boolean success(ApiResponse<?> response) {
        if (response == null) {
            return false;
        }
        Head head = response.getHead();
        return head.isSuccess();
    }
}