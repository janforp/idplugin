package com.janita.idplugin.common.entity;

import com.janita.idplugin.common.enums.CrQuestionState;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhucj
 * @since 20220415
 */
@Data
@ToString
public class CrQuestion {

    /**
     * id
     */
    private Long id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 文件名称/路径
     */
    private String filePath;

    /**
     * 名称
     */
    private String fileName;

    /**
     * 语言
     */
    private String language;

    /**
     * 问题类型
     */
    private String type;

    /**
     * 该问题的级别,类似复杂程度,数字越大问题越复杂
     */
    private String level;

    /**
     * 状态
     * 未解决,已解决,重复问题,已关闭
     *
     * @see CrQuestionState
     */
    private String state;

    /**
     * 提出问题的人
     */
    private String assignFrom;

    /**
     * 接收问题的人
     */
    private String assignTo;

    /**
     * 问题代码
     */
    private String questionCode;

    /**
     * 建议写法
     */
    private String suggestCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建git分支名称
     */
    private String createGitBranchName;

    /**
     * 解决git分支名称
     */
    private String solveGitBranchName;

    /**
     * 起始
     */
    private Integer offsetStart;

    /**
     * 结束行
     */
    private Integer offsetEnd;

    public CrQuestion() {
        // empty
    }
}