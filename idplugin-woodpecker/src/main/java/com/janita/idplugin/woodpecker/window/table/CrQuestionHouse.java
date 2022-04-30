package com.janita.idplugin.woodpecker.window.table;

import com.janita.idplugin.woodpecker.common.domain.Pair;
import com.janita.idplugin.woodpecker.common.util.CommonUtils;
import com.janita.idplugin.woodpecker.common.util.SingletonBeanFactory;
import com.janita.idplugin.woodpecker.common.wechat.WeChatService;
import com.janita.idplugin.woodpecker.common.wechat.domain.OperationType;
import com.janita.idplugin.woodpecker.domain.CrQuestion;
import com.janita.idplugin.woodpecker.domain.CrQuestionQueryRequest;

import java.util.List;

/**
 * 这一层主要负责渲染问题列表
 *
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionHouse {

    public static void add(CrQuestion question, boolean sendWeChatMsg, List<String> phoneList) {
        boolean success = SingletonBeanFactory.getCrQuestionDAO().insert(question);
        if (!success) {
            return;
        }
        CrQuestionTable.getCrQuestionList().add(question);
        String[] raw = CrQuestionTable.convertToRaw(question);
        CrQuestionTable.TABLE_MODEL.addRow(raw);
        if (sendWeChatMsg) {
            WeChatService.sendByMarkDown(question, phoneList, OperationType.add);
        }
    }

    public static void update(Integer editIndex, CrQuestion question, boolean sendWeChatMsg, List<String> phoneList) {
        boolean update = SingletonBeanFactory.getCrQuestionDAO().update(question);
        if (!update) {
            return;
        }
        CrQuestionTable.getCrQuestionList().set(editIndex, question);
        CrQuestionTable.TABLE_MODEL.removeRow(editIndex);
        String[] raw = CrQuestionTable.convertToRaw(question);
        CrQuestionTable.TABLE_MODEL.insertRow(editIndex, raw);
        if (sendWeChatMsg) {
            WeChatService.sendByMarkDown(question, phoneList,OperationType.update);
        }
    }

    public static void rerenderTable(CrQuestionQueryRequest request) {
        Pair<Boolean, List<CrQuestion>> pair = SingletonBeanFactory.getCrQuestionDAO().queryQuestion(request);
        if (!pair.getLeft()) {
            return;
        }
        List<CrQuestion> questionList = pair.getRight();
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