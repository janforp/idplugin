package com.janita.idplugin.woodpecker.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhucj
 * @since 20220415
 */
public class DataToInit {

    /**
     * 问题类型
     */
    public static final List<String> QUESTION_TYPE_LIST = Arrays.asList("建议", "性能", "缺陷", "规范", "业务", "BUG", "漏洞");

    /**
     * 问题级别
     */
    public static final List<String> LEVEL_LIST = Arrays.asList("提示", "次要", "主要", "严重", "阻断");
}