package com.janita.idplugin.common.entity;

import lombok.Builder;
import lombok.Data;

/**
 * SelectTextOffLineHolder
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@Builder
public class SelectFileInfo {

    /**
     * 语言
     */
    private String language;

    /**
     * 文件名称/路径
     */
    private String filePath;

    /**
     * 名称
     */
    private String fileName;

    /**
     * 选择文本
     */
    private String selectedText;

    /**
     * 开始行
     */
    private int offsetStart;

    /**
     * 开始行
     */
    private int offsetEnd;
}
