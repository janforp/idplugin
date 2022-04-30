package com.janita.idplugin.woodpecker.common.enums;

import com.janita.idplugin.woodpecker.setting.CrQuestionSetting;
import lombok.AllArgsConstructor;

/**
 * CrRestApiEnum
 *
 * @author zhucj
 * @since 20220324
 */
@AllArgsConstructor
public enum CrRestApiEnum {

    HEALTH("/crhelper/health/check"),

    QUESTION_ADD("/crhelper/crQuestion/add"),

    QUESTION_UPDATE("/crhelper/crQuestion/update"),

    QUESTION_QUERY("/crhelper/crQuestion/query"),

    DEVELOPER_QUERY("/crhelper/crDeveloper/query"),

    DEVELOPER_SAVE("/crhelper/crDeveloper/save");

    private final String url;

    public String getUrl() {
        String restApiDomain = CrQuestionSetting.getCrQuestionSettingFromCache().getRestApiDomain();
        return restApiDomain + url;
    }
}