package com.janita.idplugin.service.crquestion;

import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.common.request.CrQuestionQueryRequest;
import com.janita.idplugin.service.crquestion.domain.CrQuestionCreate;

import java.util.List;

/**
 * CrQuestionService
 *
 * @author zhucj
 * @since 20220324
 */
public interface ICrQuestionService {

    void save(CrDataStorageEnum storageEnum, DbConfig config, CrQuestionCreate create);

    List<CrQuestion> query(CrDataStorageEnum storageEnum, DbConfig dbConfig, CrQuestionQueryRequest request);
}
