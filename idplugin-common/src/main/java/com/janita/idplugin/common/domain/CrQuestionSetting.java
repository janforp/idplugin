package com.janita.idplugin.common.domain;

import com.janita.idplugin.common.enums.CrDataStorageEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * CrQuestionSetting
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@EqualsAndHashCode
public class CrQuestionSetting {

    private CrDataStorageEnum storageWay;

    private String dbUrl;

    private String dbUsername;

    private String dbPwd;

    private String restApiDomain;
}