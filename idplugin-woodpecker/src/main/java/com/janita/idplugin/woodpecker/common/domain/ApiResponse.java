package com.janita.idplugin.woodpecker.common.domain;

import lombok.Getter;

/**
 * ResultVO
 *
 * @author zhucj
 * @since 20220324
 */
public class ApiResponse<T> {

    @Getter
    private Head head;

    @Getter
    private T data;
}