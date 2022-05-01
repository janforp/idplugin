package com.janita.idplugin.woodpecker.table;

import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.idea.base.util.CommonUtils;
import com.janita.idplugin.woodpecker.renderer.CrQuestionTableRenderer;

import javax.swing.*;
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

    /**
     * @see CrQuestionTableRenderer 添加项目的时候注意修改这个
     */
    public static String[] HEAD = { "id", "项目", "文件", "类型", "级别", "迭代", "指派给", "提问者", "状态" };

    public static DefaultTableModel TABLE_MODEL = new DefaultTableModel(null, HEAD);

    private static final List<CrQuestion> CR_QUESTION_LIST = new ArrayList<>();

    public static List<CrQuestion> getCrQuestionList() {
        return CR_QUESTION_LIST;
    }

    /**
     * 表与数据模型绑定
     *
     * @param table 表
     */
    public static void bindModelWithTable(JTable table) {
        if (table == null) {
            return;
        }
        table.setModel(TABLE_MODEL);
    }

    public static void add(CrQuestion question) {
        getCrQuestionList().add(question);
        String[] raw = CrQuestionTable.convertToRaw(question);
        CrQuestionTable.TABLE_MODEL.addRow(raw);
    }

    public static void update(Integer editIndex, CrQuestion question) {
        getCrQuestionList().set(editIndex, question);
        CrQuestionTable.TABLE_MODEL.removeRow(editIndex);
        String[] raw = CrQuestionTable.convertToRaw(question);
        CrQuestionTable.TABLE_MODEL.insertRow(editIndex, raw);
    }

    public static void rerenderTable(List<CrQuestion> questionList) {
        getCrQuestionList().clear();
        CommonUtils.clearDefaultTableModel(CrQuestionTable.TABLE_MODEL);
        if (questionList == null || questionList.size() == 0) {
            return;
        }
        for (CrQuestion question : questionList) {
            getCrQuestionList().add(question);
            String[] raw = CrQuestionTable.convertToRaw(question);
            CrQuestionTable.TABLE_MODEL.addRow(raw);
        }
    }

    private static String[] convertToRaw(CrQuestion question) {
        String[] raw = new String[9];
        raw[0] = question.getId() == null ? null : question.getId().toString();
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