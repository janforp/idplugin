package com.janita.idplugin.remote.api;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Head
 *
 * @author zhucj
 * @since 20220324
 */
@NoArgsConstructor
public class Head {

    @Getter
    private boolean success;

    @Getter
    private String msg;
}