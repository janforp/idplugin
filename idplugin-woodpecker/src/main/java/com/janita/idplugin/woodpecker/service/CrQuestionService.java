package com.janita.idplugin.woodpecker.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.janita.idplugin.common.constant.PluginConstant;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.service.crdeveloper.ICrDeveloperService;
import com.janita.idplugin.service.crdeveloper.factory.CrDeveloperServiceFactory;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.request.CrDeveloperQueryRequest;
import com.janita.idplugin.woodpecker.setting.CrQuestionSetting;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * CrQuestionService
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionService {

    private CrQuestionService() {
    }

    public Pair<Boolean, List<CrDeveloper>> queryAssignName(String projectName) {
        CrDeveloperQueryRequest request = new CrDeveloperQueryRequest();
        request.setProjectNameSet(Sets.newHashSet(projectName));

        CrDataStorageEnum storageEnum = CrQuestionSetting.getStorageWayFromCache();
        ICrDeveloperService developerService = CrDeveloperServiceFactory.getICrDeveloperService(storageEnum);
        List<CrDeveloper> developerList = developerService.query(storageEnum, CrQuestionSetting.getDbConfig(), request);
        if (CollectionUtils.isNotEmpty(developerList)) {
            return Pair.of(true, developerList);
        }
        return Pair.of(true, Lists.newArrayList(new CrDeveloper(PluginConstant.PLEASE_MANUAL_ASSIGN)));
    }
}