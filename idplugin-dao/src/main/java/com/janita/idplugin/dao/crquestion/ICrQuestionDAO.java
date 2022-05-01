package com.janita.idplugin.dao.crquestion;

import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.domain.Pair;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.request.CrQuestionQueryRequest;

import java.util.List;

/**
 * ICrQuestionDAO
 *
 * @author zhucj
 * @since 20220324
 */
public interface ICrQuestionDAO {

    /**
     * 健康检查
     *
     * @return 是否健康
     */
    boolean checkHealth(CrQuestionSetting setting);

    /**
     * 添加
     *
     * @param question 问题
     * @return 成功/失败
     */
    boolean insert(CrQuestionSetting setting, CrQuestion question);

    /**
     * 修改
     *
     * @param question 问题
     * @return 成功/失败
     */
    boolean update(CrQuestionSetting setting,CrQuestion question);

    /**
     * 查询
     *
     * @param request 参数
     * @return 成功/失败
     */
    Pair<Boolean, List<CrQuestion>> queryQuestion(CrQuestionSetting setting,CrQuestionQueryRequest request);
}