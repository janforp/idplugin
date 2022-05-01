package com.janita.idplugin.service.crdeveloper;

import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.domain.Pair;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.request.CrDeveloperQueryRequest;
import com.janita.idplugin.common.request.CrDeveloperSaveRequest;

import java.util.List;

/**
 * ICrDeveloperService
 *
 * @author zhucj
 * @since 20220324
 */
public interface ICrDeveloperService {

    /**
     * 保存
     *
     * @param setting 配置
     * @param request 参数
     */
    void save(CrQuestionSetting setting, CrDeveloperSaveRequest request);

    /**
     * 查询开发列表
     *
     * @param setting 配置
     * @param request 参数
     * @return 列表
     */
    List<CrDeveloper> query(CrQuestionSetting setting, CrDeveloperQueryRequest request);

    /**
     * 根据项目名称查询
     *
     * @param setting 配置
     * @param projectName 项目名称
     * @return 开发列表
     */
    Pair<Boolean, List<CrDeveloper>> queryAssignName(CrQuestionSetting setting, String projectName);
}