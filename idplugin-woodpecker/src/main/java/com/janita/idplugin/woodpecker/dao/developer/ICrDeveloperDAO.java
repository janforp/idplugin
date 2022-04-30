package com.janita.idplugin.woodpecker.dao.developer;

import com.janita.idplugin.woodpecker.common.domain.Pair;
import com.janita.idplugin.woodpecker.domain.CrDeveloper;
import com.janita.idplugin.woodpecker.domain.CrDeveloperQueryRequest;
import com.janita.idplugin.woodpecker.domain.CrDeveloperSaveRequest;

import java.util.List;

/**
 * ICrDeveloperDAP
 *
 * @author zhucj
 * @since 20220324
 */
public interface ICrDeveloperDAO {

    /**
     * 保存
     *
     * @param request 参数
     * @return 结果
     */
    boolean save(CrDeveloperSaveRequest request);

    /**
     * 查询
     *
     * @param request 参数
     * @return 成功/失败
     */
    Pair<Boolean, List<CrDeveloper>> queryDeveloper(CrDeveloperQueryRequest request);
}