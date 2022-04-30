package com.janita.idplugin.dao.crdeveloper.impl;

import com.alibaba.fastjson.JSONObject;
import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.dao.crquestion.impl.CrQuestionRestApiDAO;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.enums.CrRestApiEnum;
import com.janita.idplugin.dao.crdeveloper.ICrDeveloperDAO;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.request.CrDeveloperQueryRequest;
import com.janita.idplugin.common.request.CrDeveloperSaveRequest;
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
    public boolean save(CrDataStorageEnum storageEnum,DbConfig config, CrDeveloperSaveRequest request) {
        Boolean success = CrQuestionRestApiDAO.post(config, CrRestApiEnum.DEVELOPER_SAVE, request);
        return BooleanUtils.isTrue(success);
    }

    @Override
    public Pair<Boolean, List<CrDeveloper>> queryDeveloper(CrDataStorageEnum storageEnum,DbConfig config, CrDeveloperQueryRequest request) {
        List list = CrQuestionRestApiDAO.post(config, CrRestApiEnum.DEVELOPER_QUERY, request);
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