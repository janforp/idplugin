package com.janita.idplugin.woodpecker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @author zhucj
 * @since 20220415
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CrQuestionQueryRequest {

    /**
     * 当前 project 下的所有仓库名称
     */
    private Set<String> projectNameSet;

    /**
     * 状态
     */
    private Set<String> stateSet;
}