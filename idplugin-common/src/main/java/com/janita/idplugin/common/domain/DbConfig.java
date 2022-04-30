package com.janita.idplugin.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DbConfig
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@AllArgsConstructor
public class DbConfig {

    private String url;

    private String username;

    private String pwd;

    private String domain;
}
