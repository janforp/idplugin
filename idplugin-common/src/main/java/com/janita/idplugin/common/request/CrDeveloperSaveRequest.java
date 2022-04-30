package com.janita.idplugin.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CrDeveloperAddRequest
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrDeveloperSaveRequest {

    private String name;

    private String email;

    private String phone;
}