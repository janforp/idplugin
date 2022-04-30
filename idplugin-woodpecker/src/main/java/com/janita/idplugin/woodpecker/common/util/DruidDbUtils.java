package com.janita.idplugin.woodpecker.common.util;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * DruidDbUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class DruidDbUtils {

    /**
     * DruidDataSource druidDataSource = new DruidDataSource();
     * druidDataSource.setUrl(jdbcUrl);
     * druidDataSource.setUsername(username);
     * druidDataSource.setPassword(password);
     * druidDataSource.setConnectionErrorRetryAttempts(3); // 失败后重连的次数
     * druidDataSource.setBreakAfterAcquireFailure(true); // 请求失败之后中断
     * druidDataSource.setMaxWait(5000);//最长等待时间（超时时间）--感谢qq_36273770指正
     */
    public static DataSource getDataSource(String url, String username, String pwd) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(pwd);
        // 失败后重连的次数
        druidDataSource.setConnectionErrorRetryAttempts(3);
        // 请求失败之后中断
        druidDataSource.setBreakAfterAcquireFailure(true);
        // 最长等待时间（超时时间）
        druidDataSource.setMaxWait(5000);
        druidDataSource.setMaxActive(1);
        druidDataSource.setMinIdle(0);
        druidDataSource.setLoginTimeout(10);
        return druidDataSource;
    }
}