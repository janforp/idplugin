package com.janita.idplugin.service.crdeveloper;

import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
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

    void save(CrDataStorageEnum storageEnum, DbConfig config, CrDeveloperSaveRequest request);

    List<CrDeveloper> query(CrDataStorageEnum storageEnum, DbConfig dbConfig, CrDeveloperQueryRequest request);

    Pair<Boolean, List<CrDeveloper>> queryAssignName(CrDataStorageEnum storageEnum, DbConfig config, String projectName);
}
