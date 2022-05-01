package com.janita.idplugin.service.crdeveloper;

import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.domain.Pair;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.request.CrDeveloperQueryRequest;
import com.janita.idplugin.common.request.CrDeveloperSaveRequest;

import java.util.List;

/**
 * ICrDeveloperService
 *
 * @author zhucj
 * @since 20220324
 */
public interface ICrDeveloperService {

    void save(CrQuestionSetting setting, CrDeveloperSaveRequest request);

    List<CrDeveloper> query(CrQuestionSetting setting, CrDeveloperQueryRequest request);

    Pair<Boolean, List<CrDeveloper>> queryAssignName(CrQuestionSetting setting, String projectName);
}
