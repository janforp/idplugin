package com.janita.idplugin.service.crquestion;

import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.request.CrQuestionQueryRequest;
import com.janita.idplugin.service.crquestion.domain.CrQuestionSaveRequest;

import java.util.List;

/**
 * CrQuestionService
 *
 * @author zhucj
 * @since 20220324
 */
public interface ICrQuestionService {

    void save(CrQuestionSetting setting, CrQuestionSaveRequest create);

    List<CrQuestion> query(CrQuestionSetting setting, CrQuestionQueryRequest request);
}
