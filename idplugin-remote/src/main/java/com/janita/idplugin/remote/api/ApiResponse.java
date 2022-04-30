package com.janita.idplugin.remote.api;

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