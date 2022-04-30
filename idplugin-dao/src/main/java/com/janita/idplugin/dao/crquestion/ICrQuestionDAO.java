package com.janita.idplugin.dao.crquestion;

import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
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
    boolean checkHealth(CrDataStorageEnum storageEnum,DbConfig config);

    /**
     * 添加
     *
     * @param question 问题
     * @return 成功/失败
     */
    boolean insert(CrDataStorageEnum storageEnum,DbConfig config, CrQuestion question);

    /**
     * 修改
     *
     * @param question 问题
     * @return 成功/失败
     */
    boolean update(CrDataStorageEnum storageEnum,DbConfig config,CrQuestion question);

    /**
     * 查询
     *
     * @param request 参数
     * @return 成功/失败
     */
    Pair<Boolean, List<CrQuestion>> queryQuestion(CrDataStorageEnum storageEnum,DbConfig config,CrQuestionQueryRequest request);
}