package com.janita.idplugin.remote.db.impl;

import com.janita.idplugin.common.IDatabaseService;
import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.remote.util.DruidDbUtils;
import com.janita.idplugin.remote.db.AbstractIDatabaseService;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * MySqlDatabaseServiceImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class MySqlDatabaseServiceImpl extends AbstractIDatabaseService {

    private static final IDatabaseService INSTANCE = new MySqlDatabaseServiceImpl();

    public static IDatabaseService getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Connection getConnectionByConfig(DbConfig config) {
        return super.getConnectionByConfig(config);
    }

    private MySqlDatabaseServiceImpl() {

    }

    @Override
    protected DataSource initDataSource(String url, String username, String pwd) {
        try {
            source = DruidDbUtils.getDataSource(url, username, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }

    @Override
    protected String getQuestionTableSql() {
        return "create table IF NOT EXISTS cr_question\n"
                + "(\n"
                + "    id                     bigint auto_increment\n"
                + "        primary key,\n"
                + "    project_name           varchar(255)     not null comment '项目名称',\n"
                + "    file_path              varchar(255)     not null comment '文件名称/路径',\n"
                + "    file_name              varchar(255)     not null comment '文件名称',\n"
                + "    language               varchar(255)     null comment '语言',\n"
                + "    type                   varchar(255)     not null comment '问题类型',\n"
                + "    level                  varchar(255)     not null comment '该问题的级别',\n"
                + "    state                  varchar(255)     not null comment '状态',\n"
                + "    assign_from            varchar(255)     not null comment '提问人',\n"
                + "    assign_to              varchar(255)     not null comment '指派给',\n"
                + "    question_code          mediumtext       not null comment '问题代码',\n"
                + "    suggest_code           mediumtext       null comment '建议写法',\n"
                + "    description            mediumtext       null comment '描述',\n"
                + "    create_git_branch_name varchar(255)     not null comment '创建git分支名称',\n"
                + "    solve_git_branch_name  varchar(255)     null comment '解决git分支名称',\n"
                + "    solve_time             datetime(6)      null comment '解决时间',\n"
                + "    offset_start           int              not null comment '起始偏移量',\n"
                + "    offset_end             int              not null comment '结束偏移量',\n"
                + "    is_delete              bigint default 0 null comment '逻辑删除',\n"
                + "    creator_id             varchar(255)     null comment '创建人',\n"
                + "    modifier_id            varchar(255)     null comment '修改人',\n"
                + "    create_date            datetime(6)      null comment '修改人',\n"
                + "    modify_date            datetime(6)      null comment '修改人'\n"
                + ")";
    }

    @Override
    protected String getDeveloperTableSql() {
        return "create table IF NOT EXISTS cr_developer\n"
                + "(\n"
                + "    id          bigint auto_increment\n"
                + "        primary key,\n"
                + "    name        varchar(255)                       not null comment '姓名，如zhucj或者朱晨剑',\n"
                + "    email       varchar(255)                       null comment '有效，如zhucj@xxx.com',\n"
                + "    phone       varchar(255)                       null comment '手机',\n"
                + "    is_delete   bigint   default 0                 null comment '逻辑删除',\n"
                + "    creator_id  varchar(255)                       null comment '创建人',\n"
                + "    modifier_id varchar(255)                       null comment '修改人',\n"
                + "    create_date datetime default CURRENT_TIMESTAMP null comment '修改人',\n"
                + "    modify_date datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改人'\n"
                + ")";
    }
}
