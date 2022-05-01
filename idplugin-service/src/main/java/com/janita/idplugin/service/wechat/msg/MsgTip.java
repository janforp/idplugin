package com.janita.idplugin.service.wechat.msg;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * * {
 * *     "msgtype": "markdown",
 * *     "markdown": {
 * *         "content": "实时新增用户反馈<font color=\"warning\">132例</font>，请相关同事注意。\n
 * *          >类型:<font color=\"comment\">用户反馈</font>
 * *          >普通用户反馈:<font color=\"comment\">117例</font>
 * *          >VIP用户反馈:<font color=\"comment\">15例</font>"
 * *     }
 * * }
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@AllArgsConstructor
public class MsgTip {

    private String title;

    private MsgColor color;

    private String content;
}
