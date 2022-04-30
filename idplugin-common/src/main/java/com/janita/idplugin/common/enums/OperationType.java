package com.janita.idplugin.common.enums;

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

    add("你有新的CodeReview消息，请注意"),

    update("CodeReview问题变更，请注意");

    @Getter
    private final String desc;
}
