package com.janita.idplugin.woodpecker.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class DateUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter YYYYMMDDHMS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return DEFAULT_FORMATTER.format(now);
    }

    public static String getCurrentTimeForFileName() {
        LocalDateTime now = LocalDateTime.now();
        return YYYYMMDDHMS.format(now);
    }
}