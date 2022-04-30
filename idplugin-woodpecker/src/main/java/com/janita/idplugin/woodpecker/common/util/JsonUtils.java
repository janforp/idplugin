package com.janita.idplugin.woodpecker.common.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * JsonUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class JsonUtils {

    public static <T> String toJson(T data) {
        return JSON.toJSONString(data);
    }

    public static <T> List<T> parseToList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
}
