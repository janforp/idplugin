package com.janita.idplugin.dao.crquestion.impl;

import com.janita.idplugin.common.IDatabaseService;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.CrQuestionState;
import com.janita.idplugin.common.request.CrQuestionQueryRequest;
import com.janita.idplugin.dao.BaseDAO;
import com.janita.idplugin.dao.crquestion.ICrQuestionDAO;
import com.janita.idplugin.remote.constant.DmlConstants;
import com.janita.idplugin.remote.db.factory.DatabaseServiceFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.math.BigInteger;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public boolean checkHealth(CrQuestionSetting setting) {
        IDatabaseService databaseService = DatabaseServiceFactory.getDatabaseService(setting.getStorageWay());
        return databaseService.checkConnectSuccess();
    }

    @Override
    public boolean insert(CrQuestionSetting setting, CrQuestion question) {
        IDatabaseService databaseService = DatabaseServiceFactory.getDatabaseService(setting.getStorageWay());
        Connection connection = databaseService.getConnectionByConfig(setting);
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
    public boolean update(CrQuestionSetting setting,CrQuestion question) {
        Long id = question.getId();
        IDatabaseService databaseService = DatabaseServiceFactory.getDatabaseService(setting.getStorageWay());
        Connection connection = databaseService.getConnectionByConfig(setting);
        CrQuestion oldQuestion = getBean(connection, DmlConstants.QUERY_BY_ID, id);
        if (oldQuestion == null) {
            return true;
        }
        String oldState = oldQuestion.getState();
        String newState = question.getState();
        boolean solved = false;
        if (CrQuestionState.SOLVED.getDesc().equals(newState) && !CrQuestionState.SOLVED.getDesc().equals(oldState)) {
            solved = true;
            // 之前不是已解决，则本次修改就是解决问题
            oldQuestion.setSolveGitBranchName(question.getSolveGitBranchName());
        }
        oldQuestion.setType(question.getType());
        oldQuestion.setLevel(question.getLevel());
        oldQuestion.setState(newState);
        oldQuestion.setAssignTo(question.getAssignTo());
        oldQuestion.setSuggestCode(question.getSuggestCode());
        oldQuestion.setDescription(question.getDescription());
        if (!solved) {
            return update(connection, DmlConstants.UPDATE_NOT_SOLVED,
                    oldQuestion.getType(),
                    oldQuestion.getLevel(),
                    oldQuestion.getState(),
                    oldQuestion.getAssignTo(),
                    oldQuestion.getSuggestCode(),
                    oldQuestion.getDescription(),
                    LocalDateTime.now(),
                    id);
        }
        return update(connection, DmlConstants.UPDATE_SOLVED,
                oldQuestion.getType(),
                oldQuestion.getLevel(),
                oldQuestion.getState(),
                oldQuestion.getAssignTo(),
                oldQuestion.getSuggestCode(),
                oldQuestion.getDescription(),
                LocalDateTime.now(),
                oldQuestion.getSolveGitBranchName(),
                LocalDateTime.now(),
                id);
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> queryQuestion(CrQuestionSetting setting,CrQuestionQueryRequest request) {
        Set<String> stateSet = request.getStateSet();
        IDatabaseService databaseService = DatabaseServiceFactory.getDatabaseService(setting.getStorageWay());
        Connection connection = databaseService.getConnectionByConfig(setting);
        try {
            List<CrQuestion> questionList = queryList(connection, DmlConstants.QUERY_SQL, new ArrayList<>(request.getProjectNameSet()).get(0));
            if (CollectionUtils.isEmpty(questionList)) {
                return Pair.of(true, new ArrayList<>(0));
            }
            if (CollectionUtils.isEmpty(stateSet)) {
                return Pair.of(true, questionList);

            }
            List<CrQuestion> list = questionList.stream().filter(item -> stateSet.contains(item.getState())).collect(Collectors.toList());
            return Pair.of(true, list);
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, new ArrayList<>(0));
        }    }
}
