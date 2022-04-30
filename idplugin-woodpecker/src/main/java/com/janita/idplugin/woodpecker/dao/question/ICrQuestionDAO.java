package com.janita.idplugin.woodpecker.dao.question;

import com.janita.idplugin.woodpecker.common.domain.Pair;
import com.janita.idplugin.woodpecker.domain.CrQuestion;
import com.janita.idplugin.woodpecker.domain.CrQuestionQueryRequest;

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
    boolean checkHealth();

    /**
     * 添加
     *
     * @param question 问题
     * @return 成功/失败
     */
    boolean insert(CrQuestion question);

    /**
     * 修改
     *
     * @param question 问题
     * @return 成功/失败
     */
    boolean update(CrQuestion question);


    /**
     * 查询
     *
     * @param request 参数
     * @return 成功/失败
     */
    Pair<Boolean, List<CrQuestion>> queryQuestion(CrQuestionQueryRequest request);
}