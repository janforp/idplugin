package com.janita.idplugin.woodpecker.window.table;

import com.janita.idplugin.idea.base.util.CommonUtils;
import com.janita.idplugin.woodpecker.common.wechat.WeChatService;
import com.janita.idplugin.woodpecker.common.wechat.domain.OperationType;
import com.janita.idplugin.common.entity.CrQuestion;

import java.util.List;

/**
 * 这一层主要负责渲染问题列表
 *
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionHouse {

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