package com.janita.idplugin.woodpecker.common.constant;

import lombok.experimental.UtilityClass;

/**
 * DmlConstants
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class DmlConstants {

    public final String LAST_INSERT_ROW_ID_OF_MYSQL = "SELECT LAST_INSERT_ID() id";

    public final String INSERT_QUESTION_IN_SQLITE = "INSERT INTO cr_question ("
            + "id, "
            + "project_name,"
            + " file_path, "
            + "file_name,"
            + "language, "
            + "type, "
            + "level, "
            + "state,"
            + "assign_from, "
            + "assign_to, "
            + "question_code, "
            + "suggest_code,"
            + "description, "
            + "create_git_branch_name, "
            + "solve_git_branch_name,"
            + "solve_time, "
            + "offset_start, "
            + "offset_end, "
            + "is_delete,"
            + "creator_id,"
            + " modifier_id, "
            + "create_date,"
            + " modify_date)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public final String GET_MAX_ID_NOW = "SELECT max(id) FROM cr_question";

    public final String UPDATE_SQL = "update cr_question\n"
            + "set project_name=?,\n"
            + "    file_path=?,\n"
            + "    file_name=?,\n"
            + "    language=?,\n"
            + "    type=?,\n"
            + "    level=?,\n"
            + "    state=?,\n"
            + "    assign_from=?,\n"
            + "    assign_to=?,\n"
            + "    question_code=?,\n"
            + "    better_code=?,\n"
            + "    description=?,\n"
            + "    create_git_branch_name=?,\n"
            + "    solve_git_branch_name=?,\n"
            + "    solve_time = ?,\n"
            + "    offset_start = ?,\n"
            + "    offset_end = ?\n"
            + "where id = ?";

    public final String DELETE_SQL = "UPDATE cr_question\n"
            + "SET is_delete = id\n"
            + "WHERE  id = ?";

    public final String QUERY_BY_ID = "SELECT id, project_name, file_path, file_name,\n"
            + "       language, type, level, state,\n"
            + "       assign_from, assign_to, question_code, suggest_code,\n"
            + "       description, create_git_branch_name, solve_git_branch_name,\n"
            + "       solve_time, offset_start, offset_end, is_delete,\n"
            + "       creator_id, modifier_id, create_date, modify_date\n"
            + "FROM cr_question\n"
            + "WHERE id = ? AND is_delete = 0";

    public final String UPDATE_NOT_SOLVED =
            "UPDATE cr_question\n"
                    + "SET type = ?,"
                    + "level = ?, "
                    + "state = ?, "
                    + "assign_to = ? ,"
                    + "suggest_code = ? ,"
                    + "description = ?,"
                    + "modify_date = ?\n"
                    + "WHERE id = ? AND is_delete = 0";

    public final String UPDATE_SOLVED =
            "UPDATE cr_question\n"
                    + "SET type                 = ?,\n"
                    + "    level                = ?,\n"
                    + "    state                = ?,\n"
                    + "    assign_to            = ?,\n"
                    + "    suggest_code         = ?,\n"
                    + "    description          = ?,\n"
                    + "    modify_date          = ?,\n"
                    + "    solve_git_branch_name=?,\n"
                    + "    solve_time=?\n"
                    + "WHERE id = ?\n"
                    + "  AND is_delete = 0";

    public final String QUERY_SQL = "SELECT id,\n"
            + "       project_name,\n"
            + "       file_path,\n"
            + "       file_name,\n"
            + "       language,\n"
            + "       type,\n"
            + "       level,\n"
            + "       state,\n"
            + "       assign_from,\n"
            + "       assign_to,\n"
            + "       question_code,\n"
            + "       suggest_code,\n"
            + "       description,\n"
            + "       create_git_branch_name,\n"
            + "       solve_git_branch_name,\n"
            + "       solve_time,\n"
            + "       offset_start,\n"
            + "       offset_end\n"
            + "FROM cr_question\n"
            + "WHERE project_name = ?\n"
            + "  AND is_delete = 0\n"
            + "ORDER BY create_date ASC";

    public static final String GET_DEVELOPER_BY_NAME = "SELECT name,phone,email FROM cr_developer WHERE name = ?";

    public static final String INSERT_DEVELOPER = "INSERT INTO cr_developer(name, email, phone) VALUES (?,?,?)";

    public static final String UPDATE_DEVELOPER_BY_NAME = "UPDATE cr_developer SET email = ? , phone = ? WHERE name = ?";

    public static final String QUERY_DEVELOPER_IN_NAME = "SELECT name,phone,email FROM cr_developer WHERE name IN ($)";
}