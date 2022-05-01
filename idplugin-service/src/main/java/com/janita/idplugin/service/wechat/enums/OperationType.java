package com.janita.idplugin.service.wechat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OperationType
 *
 * @author zhucj
 * @since 20220324
 */
@AllArgsConstructor
public enum OperationType {

    add("新的CodeReview问题", "你有新的CodeReview消息，请注意"),

    update("CodeReview问题变更", "CodeReview问题变更，请注意");

    @Getter
    private final String title;

    @Getter
    private final String desc;
}
