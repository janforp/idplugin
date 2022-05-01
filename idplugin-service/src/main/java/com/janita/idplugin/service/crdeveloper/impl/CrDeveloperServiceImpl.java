package com.janita.idplugin.service.crdeveloper.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.constant.PluginConstant;
import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.request.CrDeveloperQueryRequest;
import com.janita.idplugin.common.request.CrDeveloperSaveRequest;
import com.janita.idplugin.dao.crdeveloper.ICrDeveloperDAO;
import com.janita.idplugin.dao.crdeveloper.factory.CrDeveloperDaoFactory;
import com.janita.idplugin.service.crdeveloper.ICrDeveloperService;
import com.janita.idplugin.service.crdeveloper.factory.CrDeveloperServiceFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * CrDeveloperImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class CrDeveloperServiceImpl implements ICrDeveloperService {

    private static final ICrDeveloperService INSTANCE = new CrDeveloperServiceImpl();

    public static ICrDeveloperService getINSTANCE() {
        return INSTANCE;
    }

    private CrDeveloperServiceImpl() {
    }

    @Override
    public void save(CrQuestionSetting setting, CrDeveloperSaveRequest request) {
        ICrDeveloperDAO crDeveloperDAO = CrDeveloperDaoFactory.getCrDeveloperDAO(setting.getStorageWay());
        crDeveloperDAO.save(setting, request);
    }

    @Override
    public List<CrDeveloper> query(CrQuestionSetting setting, CrDeveloperQueryRequest request) {
        ICrDeveloperDAO crDeveloperDAO = CrDeveloperDaoFactory.getCrDeveloperDAO(setting.getStorageWay());
        Pair<Boolean, List<CrDeveloper>> booleanListPair = crDeveloperDAO.queryDeveloper(setting, request);
        return booleanListPair.getRight();
    }

    @Override
    public Pair<Boolean, List<CrDeveloper>> queryAssignName(CrQuestionSetting setting,String projectName) {
        CrDeveloperQueryRequest request = new CrDeveloperQueryRequest();
        request.setProjectNameSet(Sets.newHashSet(projectName));

        ICrDeveloperService developerService = CrDeveloperServiceFactory.getICrDeveloperService(setting.getStorageWay());
        List<CrDeveloper> developerList = developerService.query(setting, request);
        if (CollectionUtils.isNotEmpty(developerList)) {
            return Pair.of(true, developerList);
        }
        return Pair.of(true, Lists.newArrayList(new CrDeveloper(PluginConstant.PLEASE_MANUAL_ASSIGN)));
    }
}
