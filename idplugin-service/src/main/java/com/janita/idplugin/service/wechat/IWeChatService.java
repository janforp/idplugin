package com.janita.idplugin.service.wechat;

import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.service.wechat.enums.OperationType;

import java.util.List;

/**
 * IWeChatService
 *
 * @author zhucj
 * @since 20220324
 */
public interface IWeChatService {

    /**
     * 发送企业微信
     *
     * @param question 问题
     * @param phoneList 发送的手机号码列表
     * @param operationType 操作类型
     */
    void sendByMarkDown(CrQuestion question, List<String> phoneList, OperationType operationType);
}