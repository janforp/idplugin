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

    /**
     * 保存
     *
     * @param setting 配置
     * @param request 请求参数
     */
    void save(CrQuestionSetting setting, CrQuestionSaveRequest request);

    /**
     * 查询
     *
     * @param setting 配置
     * @param request 请求参数
     * @return 列表
     */
    List<CrQuestion> query(CrQuestionSetting setting, CrQuestionQueryRequest request);
}
