package com.janita.idplugin.common.constant;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * PluginConstant
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class PluginConstant {

    public static class WeChatConstants {

        /**
         * https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=ddee8635-2f3c-4316-996d-fc512625fb46
         */
        public static final String WE_CHAT_SEND_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=";

        /**
         * 群聊
         *
         * 80403b99-179c-4a9c-a0cc-3160c705f53a
         */
        public static final String WE_CHAT_GROUP_ROBOT_ID = "80403b99-179c-4a9c-a0cc-3160c705f53a";
    }

    public static class DbDrivers {

        public static final String SQLITE_DATABASE_DRIVER = "org.sqlite.JDBC";
    }

    /**
     * 请手动指派
     */
    public final String PLEASE_MANUAL_ASSIGN = "--请手动指派--";

    /**
     * 冒号 colon
     */
    public static final String COLON = "\"";

    /**
     * /Users/zhuchenjian/
     */
    private final String USER_HOME_PATH = System.getProperty("user.home");

    /**
     * /Users/zhuchenjian/.ideaWoodpeckerFile
     */
    public Path PROJECT_DB_DIRECTORY_PATH = Paths.get(USER_HOME_PATH, ".ideaWoodpeckerFile");

    /**
     * /Users/zhuchenjian/.ideaWoodpeckerFile/Woodpecker.db
     */
    public Path DB_FILE_PATH = PROJECT_DB_DIRECTORY_PATH.resolve("Woodpecker.db");
}