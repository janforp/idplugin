package com.janita.idplugin.woodpecker.dao.developer.impl;

import com.alibaba.fastjson.JSONObject;
import com.janita.idplugin.woodpecker.common.domain.Pair;
import com.janita.idplugin.woodpecker.common.enums.CrRestApiEnum;
import com.janita.idplugin.woodpecker.dao.developer.ICrDeveloperDAO;
import com.janita.idplugin.woodpecker.dao.question.impl.CrQuestionRestApiDAO;
import com.janita.idplugin.woodpecker.domain.CrDeveloper;
import com.janita.idplugin.woodpecker.domain.CrDeveloperQueryRequest;
import com.janita.idplugin.woodpecker.domain.CrDeveloperSaveRequest;
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

    private CrDeveloperRestApiDAO() {
    }

    @Override
    public boolean save(CrDeveloperSaveRequest request) {
        Boolean success = CrQuestionRestApiDAO.post(CrRestApiEnum.DEVELOPER_SAVE, request);
        return BooleanUtils.isTrue(success);
    }

    @Override
    public Pair<Boolean, List<CrDeveloper>> queryDeveloper(CrDeveloperQueryRequest request) {
        List list = CrQuestionRestApiDAO.post(CrRestApiEnum.DEVELOPER_QUERY, request);
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