package com.janita.idplugin.common;

import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.enums.CrDataStorageEnum;

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
     * @param storageEnum 方式
     * @param config 配置
     * @return 是否健康
     */
    boolean checkHealth(CrDataStorageEnum storageEnum, DbConfig config);
}
