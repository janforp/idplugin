package com.janita.idplugin.common;

import com.janita.idplugin.common.domain.CrQuestionSetting;

import java.sql.Connection;

/**
 * IDatabaseService
 *
 * @author zhucj
 * @since 20220324
 */
public interface IDatabaseService {

    /**
     * 获取链接
     *
     * @return 数据库链接
     */
    Connection getConnectDirectly();

    /**
     * 连接是否成功
     *
     * @return 连接是否成功
     */
    boolean checkConnectSuccess();

    /**
     * 配置发生变化
     */
    void reInitConnect(String url, String username, String pwd);

    /**
     * 释放资源
     */
    void closeResource();

    /**
     * 获取连接
     *
     * @param setting 配置
     * @return 连接
     */
    Connection getConnectionByConfig(CrQuestionSetting setting);
}