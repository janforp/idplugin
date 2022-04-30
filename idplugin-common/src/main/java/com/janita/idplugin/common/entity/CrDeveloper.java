package com.janita.idplugin.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CrDeveloperEntity
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrDeveloper {

    private String name;

    private String email;

    private String phone;

    public CrDeveloper(String name) {
        this.name = name;
    }
}