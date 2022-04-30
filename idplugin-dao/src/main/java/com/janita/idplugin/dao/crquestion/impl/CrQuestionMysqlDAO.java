package com.janita.idplugin.dao.crquestion.impl;

import com.janita.idplugin.common.IDatabaseService;
import com.janita.idplugin.common.domain.DbConfig;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.common.request.CrQuestionQueryRequest;
import com.janita.idplugin.dao.BaseDAO;
import com.janita.idplugin.dao.crquestion.ICrQuestionDAO;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.remote.constant.DmlConstants;
import com.janita.idplugin.remote.db.factory.DatabaseServiceFactory;
import org.apache.commons.lang3.BooleanUtils;

import java.math.BigInteger;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

/**
 * CrQuestionMyslDAO
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionMysqlDAO extends BaseDAO<CrQuestion> implements ICrQuestionDAO {

    private static final ICrQuestionDAO INSTANCE = new CrQuestionMysqlDAO();

    public static ICrQuestionDAO getINSTANCE() {
        return INSTANCE;
    }

    private CrQuestionMysqlDAO(){}

    @Override
    public boolean checkHealth(CrDataStorageEnum storageEnum,DbConfig config) {
        return false;
    }

    @Override
    public boolean insert(CrDataStorageEnum storageEnum,DbConfig config, CrQuestion question) {
        IDatabaseService databaseService = DatabaseServiceFactory.getDatabaseService(storageEnum);
        Connection connection = databaseService.getConnectionByConfig(config);
        boolean success = update(connection, DmlConstants.INSERT_QUESTION_IN_SQLITE,
                null,
                question.getProjectName(),
                question.getFilePath(),
                question.getFileName(),
                question.getLanguage(),
                question.getType(),
                question.getLevel(),
                question.getState(),
                question.getAssignFrom(),
                question.getAssignTo(),
                question.getQuestionCode(),
                question.getSuggestCode(),
                question.getDescription(),
                question.getCreateGitBranchName(),
                question.getSolveGitBranchName(),
                null,
                question.getOffsetStart(),
                question.getOffsetEnd(),
                0,
                question.getAssignFrom(),
                question.getAssignFrom(),
                LocalDateTime.now(),
                LocalDateTime.now());
        if (!success) {
            return false;
        }

        Pair<Boolean, Long> pair = getLastInsertId(connection);
        if (BooleanUtils.isNotTrue(pair.getLeft())) {
            return false;
        }
        question.setId(pair.getRight());
        return true;
    }

    private Pair<Boolean, Long> getLastInsertId(Connection connection) {
        Pair<Boolean, Object> pair = getValue(connection, DmlConstants.LAST_INSERT_ROW_ID_OF_MYSQL);
        if (BooleanUtils.isNotTrue(pair.getLeft())) {
            return Pair.of(false, null);
        }
        Object right = pair.getRight();
        if (right instanceof BigInteger) {
            BigInteger id = (BigInteger) right;
            return Pair.of(true, id.longValue());
        }
        return Pair.of(true, (Long) right);
    }

    @Override
    public boolean update(CrDataStorageEnum storageEnum,DbConfig config,CrQuestion question) {
        return false;
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> queryQuestion(CrDataStorageEnum storageEnum,DbConfig config,CrQuestionQueryRequest request) {
        return null;
    }
}
