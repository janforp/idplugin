package com.janita.idplugin.dao.crdeveloper;

import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.common.request.CrDeveloperQueryRequest;
import com.janita.idplugin.common.request.CrDeveloperSaveRequest;

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
    boolean save(CrDataStorageEnum storageEnum,DbConfig config, CrDeveloperSaveRequest request);

    /**
     * 查询
     *
     * @param request 参数
     * @return 成功/失败
     */
    Pair<Boolean, List<CrDeveloper>> queryDeveloper(CrDataStorageEnum storageEnum,DbConfig config, CrDeveloperQueryRequest request);
}