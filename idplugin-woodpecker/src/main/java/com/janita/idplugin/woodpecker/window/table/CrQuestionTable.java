package com.janita.idplugin.woodpecker.window.table;

import com.janita.idplugin.woodpecker.domain.CrQuestion;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * CrQuestionTable
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionTable {

    private static final List<CrQuestion> CR_QUESTION_LIST = new ArrayList<>();

    public static String[] HEAD = {"id", "项目", "文件", "类型", "级别", "迭代", "指派给", "提问者", "状态" };

    public static DefaultTableModel TABLE_MODEL = new DefaultTableModel(null, HEAD);

    public static List<CrQuestion> getCrQuestionList() {
        return CR_QUESTION_LIST;
    }

    public static String[] convertToRaw(CrQuestion question) {
        String[] raw = new String[9];
        raw[0] = question.getId() ==  null ? null : question.getId().toString();
        raw[1] = question.getProjectName();
        raw[2] = question.getFileName();
        raw[3] = question.getType();
        raw[4] = question.getLevel();
        raw[5] = question.getCreateGitBranchName();
        raw[6] = question.getAssignTo();
        raw[7] = question.getAssignFrom();
        raw[8] = question.getState();
        return raw;
    }
}