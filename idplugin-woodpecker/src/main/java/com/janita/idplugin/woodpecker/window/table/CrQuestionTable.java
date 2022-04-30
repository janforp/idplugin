package com.janita.idplugin.woodpecker.window.table;

import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.idea.base.util.CommonUtils;
import com.janita.idplugin.woodpecker.common.wechat.WeChatService;
import com.janita.idplugin.woodpecker.common.wechat.domain.OperationType;

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


    public static void add(CrQuestion question, boolean sendWeChatMsg, List<String> phoneList) {
        CrQuestionTable.getCrQuestionList().add(question);
        String[] raw = CrQuestionTable.convertToRaw(question);
        CrQuestionTable.TABLE_MODEL.addRow(raw);
        if (sendWeChatMsg) {
            WeChatService.sendByMarkDown(question, phoneList, OperationType.add);
        }
    }

    public static void update(Integer editIndex, CrQuestion question, boolean sendWeChatMsg, List<String> phoneList) {
        CrQuestionTable.getCrQuestionList().set(editIndex, question);
        CrQuestionTable.TABLE_MODEL.removeRow(editIndex);
        String[] raw = CrQuestionTable.convertToRaw(question);
        CrQuestionTable.TABLE_MODEL.insertRow(editIndex, raw);
        if (sendWeChatMsg) {
            WeChatService.sendByMarkDown(question, phoneList,OperationType.update);
        }
    }

    public static void rerenderTable(List<CrQuestion> questionList) {
        CrQuestionTable.getCrQuestionList().clear();
        CommonUtils.clearDefaultTableModel(CrQuestionTable.TABLE_MODEL);
        if (questionList == null || questionList.size() == 0) {
            return;
        }
        for (CrQuestion question : questionList) {
            CrQuestionTable.getCrQuestionList().add(question);
            String[] raw = CrQuestionTable.convertToRaw(question);
            CrQuestionTable.TABLE_MODEL.addRow(raw);
        }
    }
}