package com.janita.idplugin.dao.crdeveloper.impl;

import com.alibaba.fastjson.JSONObject;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.enums.CrRestApiEnum;
import com.janita.idplugin.common.request.CrDeveloperQueryRequest;
import com.janita.idplugin.common.request.CrDeveloperSaveRequest;
import com.janita.idplugin.dao.crdeveloper.ICrDeveloperDAO;
import com.janita.idplugin.remote.rest.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * CrDeveloperRestApiDAO
 *
 * @author zhucj
 * @since 20220324
 */
public class CrDeveloperRestApiDAO implements ICrDeveloperDAO {
    private static final ICrDeveloperDAO INSTANCE = new CrDeveloperRestApiDAO();

    public static ICrDeveloperDAO getINSTANCE() {
        return INSTANCE;
    }
    private CrDeveloperRestApiDAO() {
    }

    @Override
    public boolean save(CrQuestionSetting setting,CrDeveloperSaveRequest request) {
        Boolean success = HttpUtils.post(setting, CrRestApiEnum.DEVELOPER_SAVE, request);
        return BooleanUtils.isTrue(success);
    }

    @Override
    public Pair<Boolean, List<CrDeveloper>> queryDeveloper(CrQuestionSetting setting,CrDeveloperQueryRequest request) {
        List list = HttpUtils.post(setting, CrRestApiEnum.DEVELOPER_QUERY, request);
        if (CollectionUtils.isEmpty(list)) {
            return Pair.of(true, new ArrayList<>(0));
        }
        List<CrDeveloper> developerList = new ArrayList<>();
        for (Object obj : list) {
            LinkedHashMap map = (LinkedHashMap) obj;
            CrDeveloper question = JSONObject.parseObject(JSONObject.toJSONString(map), CrDeveloper.class);
            developerList.add(question);
        }
        return Pair.of(true, developerList);
    }
}