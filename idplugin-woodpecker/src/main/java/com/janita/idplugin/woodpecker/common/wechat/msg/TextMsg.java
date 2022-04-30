package com.janita.idplugin.woodpecker.common.wechat.msg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * MarkdownMsg
 *
 * @author zhucj
 * @since 20220324
 */
public class TextMsg {

    @JSONField(name = "msgtype")
    @Getter
    private final String msgType = "text";

    @Setter
    @Getter
    private Text text;

    @Data
    public static class Text {

        private String content;

        @JSONField(name = "mentioned_list")
        private List<String> mentionedList;

        @JSONField(name = "mentioned_mobile_list")
        private List<String> mentionedMobileList;
    }
}
