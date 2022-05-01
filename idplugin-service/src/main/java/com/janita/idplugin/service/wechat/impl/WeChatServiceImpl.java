package com.janita.idplugin.service.wechat.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.janita.idplugin.common.constant.PluginConstant;
import com.janita.idplugin.common.domain.MsgColor;
import com.janita.idplugin.common.domain.MsgTip;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.OperationType;
import com.janita.idplugin.common.util.WeChatUtils;
import com.janita.idplugin.service.wechat.IWeChatService;
import com.janita.idplugin.service.wechat.msg.MarkdownMsg;
import com.janita.idplugin.service.wechat.msg.TextMsg;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * WeChatServiceImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class WeChatServiceImpl implements IWeChatService {

    private static final IWeChatService INSTANCE = new WeChatServiceImpl();

    public static IWeChatService getINSTANCE() {
        return INSTANCE;
    }

    private WeChatServiceImpl() {
    }

    @Override
    public void sendByMarkDown(CrQuestion question, List<String> phoneList, OperationType operationType) {
        MarkdownMsg markdownMsg = new MarkdownMsg();
        MarkdownMsg.MarkDown markDown = new MarkdownMsg.MarkDown();
        markDown.setMentionedList(Lists.newArrayList(""));
        markDown.setMentionedMobileList(Lists.newArrayList("13738053603"));
        markdownMsg.setMarkdown(markDown);
        String content = buildMarkdownContent("CodeReview问题", Lists.newArrayList(
                new MsgTip("工程", MsgColor.info, question.getProjectName()),
                new MsgTip("文件", MsgColor.info, question.getFileName()),
                new MsgTip("分支", MsgColor.info, question.getCreateGitBranchName()),
                new MsgTip("类型", MsgColor.warning, question.getType()),
                new MsgTip("级别", MsgColor.warning, question.getLevel()),
                new MsgTip("状态", MsgColor.warning, question.getState()),
                new MsgTip("指派给", MsgColor.info, question.getAssignTo()),
                new MsgTip("创建人", MsgColor.info, question.getAssignFrom())));
        markDown.setContent(content);
        WeChatUtils.sendPost(PluginConstant.WeChatConstants.WE_CHAT_GROUP_ROBOT_ID, JSON.toJSONString(markdownMsg));
        if (CollectionUtils.isEmpty(phoneList)) {
            return;
        }
        sendByText(operationType.getDesc(), null, phoneList);
    }

    private static void sendByText(String content, List<String> mentionedList, List<String> mentionedMobileList) {
        TextMsg textMsg = new TextMsg();
        TextMsg.Text text = new TextMsg.Text();
        text.setMentionedList(mentionedList);
        text.setMentionedMobileList(mentionedMobileList);
        text.setContent(content);
        textMsg.setText(text);
        WeChatUtils.sendPost(PluginConstant.WeChatConstants.WE_CHAT_GROUP_ROBOT_ID, JSON.toJSONString(textMsg));
    }

    private static String buildMarkdownContent(String title, List<MsgTip> tipList) {
        StringBuilder content = new StringBuilder(title);
        for (MsgTip msgTip : tipList) {
            String oneTip = buildOneTip(msgTip);
            content.append(oneTip);
        }
        return content.toString();
    }

    private static String buildOneTip(MsgTip tip) {
        return "\n" + tip.getTitle() + "：<font color=" + PluginConstant.COLON + tip.getColor().name() + PluginConstant.COLON + ">" + tip.getContent() + "</font>";
    }
}