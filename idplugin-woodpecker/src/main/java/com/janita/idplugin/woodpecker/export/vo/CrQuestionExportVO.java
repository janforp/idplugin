package com.janita.idplugin.woodpecker.export.vo;

import lombok.Data;

/**
 * CrQuestionExportVO
 *
 * @author zhucj
 * @since 20220324
 */
@Data
public class CrQuestionExportVO {

    /**
     * 该问题的级别,类似复杂程度,数字越大问题越复杂
     */
    private String level;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 问题类型
     */
    private String type;

    /**
     * 类名称
     */
    private String fileName;

    /**
     * 问题代码
     */
    private String questionCode;

    /**
     * 建议写法
     */
    private String betterCode;

    /**
     * 描述
     */
    private String desc;

    /**
     * 接收问题的人
     */
    private String assignTo;

    /**
     * git分支名称
     */
    private String createGitBranchName;

    /**
     * 状态
     * 未解决,已解决,重复问题,已关闭
     */
    private String state;
}
