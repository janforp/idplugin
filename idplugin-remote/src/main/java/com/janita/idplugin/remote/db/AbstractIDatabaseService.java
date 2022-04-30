package com.janita.idplugin.remote.db;

import com.janita.idplugin.common.IDatabaseService;
import com.mysql.cj.jdbc.ConnectionImpl;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * AbstractIDatabaseService
 *
 * @author zhucj
 * @since 20220324
 */
public abstract class AbstractIDatabaseService implements IDatabaseService {

    public static final Connection INVALID_CONNECT = new InvalidConnect();

    protected DataSource source;

    protected Connection connection;

    protected void createFileAndDir() {
        // empty
    }

    @Override
    public void reInitConnect(String url, String username, String pwd) {
        this.closeResource();
        this.source = initDataSource(url, username, pwd);
        this.connection = getConnection(url, username, pwd);
        // 如果不存在,创建DB文件
        createFileAndDir();
        if (this.connection == INVALID_CONNECT) {
            return;
        }
        this.initTable(url, username, pwd);
    }

    @Override
    public Connection getConnectDirectly() {
        return connection;
    }

    @Override
    public boolean checkConnectSuccess() {
        try {
            return connection != null && connection != INVALID_CONNECT && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Connection getConnection(String url, String username, String pwd) {
        if (source == null) {
            source = initDataSource(url, username, pwd);
        }
        if (connection == null || connection == INVALID_CONNECT) {
            try {
                connection = source.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
                connection = INVALID_CONNECT;
            }
        }
        try {
            if (connection != INVALID_CONNECT && connection.isClosed()) {
                try {
                    connection = source.getConnection();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    connection = INVALID_CONNECT;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (connection == null) {
            return INVALID_CONNECT;
        }
        return connection;
    }

    private void initTable(String url, String username, String pwd) {
        String createQuestionSQL = getQuestionTableSql();
        String developerTableSql = getDeveloperTableSql();
        try {
            QueryRunner queryRunner = new QueryRunner(source);
            queryRunner.update(getConnection(url, username, pwd), createQuestionSQL);
            queryRunner.update(getConnection(url, username, pwd), developerTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeResource() {
        source = null;
        if (connection == INVALID_CONNECT) {
            connection = null;
            return;
        }
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据源初始化
     *
     * @return 数据源初始化
     */
    protected abstract DataSource initDataSource(String url, String username, String pwd);

    /**
     * 创建表
     *
     * @return sql
     */
    protected abstract String getQuestionTableSql();

    /**
     * 创建表
     *
     * @return sql
     */
    protected abstract String getDeveloperTableSql();

    private static class InvalidConnect extends ConnectionImpl {

        private InvalidConnect() {
        }

        @Override
        public boolean isClosed() {
            return true;
        }

        @Override
        public String toString() {
            return "数据库配置异常";
        }
    }
}