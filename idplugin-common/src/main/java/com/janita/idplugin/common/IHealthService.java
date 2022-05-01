package com.janita.idplugin.common;

import com.janita.idplugin.common.domain.CrQuestionSetting;

/**
 * IHealthService
 *
 * @author zhucj
 * @since 20220324
 */
public interface IHealthService {

    /**
     * 健康检查
     *
     * @param setting 配置
     * @return 是否健康
     */
    boolean checkHealth(CrQuestionSetting setting);
}
