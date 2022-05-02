package com.janita.idplugin.remote.db.impl;

import com.janita.idplugin.common.IDatabaseService;
import com.janita.idplugin.common.constant.PluginConstant;
import com.janita.idplugin.remote.db.AbstractIDatabaseService;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * SqliteDatabaseServiceImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class SqliteDatabaseServiceImpl extends AbstractIDatabaseService {

    /**
     * jdbc:sqlite:/Users/zhuchenjian/.ideaWoodpeckerFile/Woodpecker.db
     */
    private static final String DATABASE_URL = "jdbc:sqlite:" + PluginConstant.DB_FILE_PATH;

    private static final IDatabaseService INSTANCE = new SqliteDatabaseServiceImpl();

    public static IDatabaseService getINSTANCE() {
        return INSTANCE;
    }

    private SqliteDatabaseServiceImpl() {

    }

    @Override
    protected BasicDataSource initDataSource(String url, String username, String pwd) {
        //创建了DBCP的数据库连接池
        BasicDataSource source = new BasicDataSource();
        try {
            //设置基本信息
            source.setMaxActive(1);
            source.setDriverClassName(PluginConstant.DbDrivers.SQLITE_DATABASE_DRIVER);
            source.setUrl(DATABASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }

    @Override
    protected String getQuestionTableSql() {
        return "create table IF NOT EXISTS cr_question\n"
                + "(\n"
                + "    id                     BIGINT PRIMARY KEY ,\n"
                + "    project_name           text              not null,\n"
                + "    file_path              text              not null,\n"
                + "    file_name              text              not null,\n"
                + "    language               text              null,\n"
                + "    type                   text              not null,\n"
                + "    level                  text              not null,\n"
                + "    state                  text              not null,\n"
                + "    assign_from            text              not null,\n"
                + "    assign_to              text              not null,\n"
                + "    question_code          text              not null,\n"
                + "    suggest_code           text              null,\n"
                + "    description            text              null,\n"
                + "    create_git_branch_name text              not null,\n"
                + "    solve_git_branch_name  text              null,\n"
                + "    solve_time             DATETIME          null,\n"
                + "    offset_start           int               not null,\n"
                + "    offset_end             int               not null,\n"
                + "    is_delete              BIGINT default 0 null,\n"
                + "    creator_id             text              null,\n"
                + "    modifier_id            text              null,\n"
                + "    create_date            DATETIME          null,\n"
                + "    modify_date            DATETIME          null\n"
                + ")";
    }

    @Override
    protected String getDeveloperTableSql() {
        return "create table IF NOT EXISTS cr_developer\n"
                + "(\n"
                + "    id          INTEGER primary key AUTOINCREMENT,\n"
                + "    name        text                               not null,\n"
                + "    email       text                               null,\n"
                + "    phone       text                               null,\n"
                + "    is_delete   INTEGER   default 0                 null,\n"
                + "    creator_id  text                               null,\n"
                + "    modifier_id text                               null,\n"
                + "    create_date datetime  null,\n"
                + "    modify_date datetime\n"
                + ")";
    }

    /**
     * 如果不存在目录和文件就创建
     */
    protected void createFileAndDir() {
        //"C:\Users\Administrator\.ideaWoodpeckerFile"
        Path projectDbDirectoryPath = PluginConstant.PROJECT_DB_DIRECTORY_PATH;
        if (!Files.exists(projectDbDirectoryPath)) {
            try {
                Files.createDirectories(projectDbDirectoryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //"C:\Users\Administrator\.ideaWoodpeckerFileNotebooks.db"
        Path dbFilePath = PluginConstant.DB_FILE_PATH;
        if (!Files.exists(dbFilePath)) {
            try {
                Files.createFile(dbFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}