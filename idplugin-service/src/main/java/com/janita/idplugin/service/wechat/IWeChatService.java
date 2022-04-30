package com.janita.idplugin.service.wechat;

import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.OperationType;

import java.util.List;

/**
 * IWeChatService
 *
 * @author zhucj
 * @since 20220324
 */
public interface IWeChatService {

    void sendByMarkDown(CrQuestion question, List<String> phoneList, OperationType operationType);
}