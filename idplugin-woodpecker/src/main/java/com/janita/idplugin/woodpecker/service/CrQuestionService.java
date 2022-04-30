package com.janita.idplugin.woodpecker.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.janita.idplugin.woodpecker.common.constant.PluginConstant;
import com.janita.idplugin.woodpecker.common.domain.Pair;
import com.janita.idplugin.woodpecker.common.util.SingletonBeanFactory;
import com.janita.idplugin.woodpecker.domain.CrDeveloper;
import com.janita.idplugin.woodpecker.domain.CrDeveloperQueryRequest;
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
        Pair<Boolean, List<CrDeveloper>> developerPair = SingletonBeanFactory.getCrDeveloperDAO().queryDeveloper(request);
        if (!developerPair.getLeft()) {
            return Pair.of(false, null);
        }
        List<CrDeveloper> developerList = developerPair.getRight();
        if (CollectionUtils.isNotEmpty(developerList)) {
            return Pair.of(true, developerList);
        }
        return Pair.of(true, Lists.newArrayList(new CrDeveloper(PluginConstant.PLEASE_MANUAL_ASSIGN)));
    }
}