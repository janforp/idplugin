package com.janita.idplugin.woodpecker.dao.question.impl;

import com.janita.idplugin.woodpecker.common.constant.DmlConstants;
import com.janita.idplugin.remote.api.Pair;
import com.janita.idplugin.woodpecker.common.enums.CrQuestionState;
import com.janita.idplugin.woodpecker.common.util.SingletonBeanFactory;
import com.janita.idplugin.woodpecker.dao.BaseDAO;
import com.janita.idplugin.woodpecker.dao.question.ICrQuestionDAO;
import com.janita.idplugin.woodpecker.dao.question.dataobject.CrSqliteQuestionDO;
import com.janita.idplugin.remote.db.IDatabaseService;
import com.janita.idplugin.woodpecker.domain.CrQuestion;
import com.janita.idplugin.woodpecker.domain.CrQuestionQueryRequest;
import com.janita.idplugin.woodpecker.setting.CrQuestionSetting;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CrQuestionMySqlDAO
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionSqliteDAO extends BaseDAO<CrSqliteQuestionDO> implements ICrQuestionDAO {

    @Override
    public boolean checkHealth() {
        return SingletonBeanFactory.getSqliteDatabaseServiceImpl().checkConnectSuccess();
    }

    @Override
    public boolean insert(CrQuestion question) {
        IDatabaseService databaseService = SingletonBeanFactory.getSqliteDatabaseServiceImpl();
        CrQuestionSetting cache = CrQuestionSetting.getCrQuestionSettingFromCache();
        Connection connection = databaseService.getConnection(cache.getDbUrl(),cache.getDbUsername(),cache.getDbPwd());
        Pair<Boolean, Integer> pair = getValue(connection, DmlConstants.GET_MAX_ID_NOW);
        if (!pair.getLeft()) {
            return false;
        }
        Integer right = pair.getRight();
        if (right == null) {
            right = 0;
        }
        Long maxId = Long.valueOf(right);
        maxId = ObjectUtils.defaultIfNull(maxId, 0L);
        Long nextId = maxId + 1;

        boolean success = update(connection, DmlConstants.INSERT_QUESTION_IN_SQLITE,
                nextId,
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
        question.setId(nextId);
        return true;
    }

    @Override
    public boolean update(CrQuestion question) {
        Long id = question.getId();
        IDatabaseService databaseService = SingletonBeanFactory.getSqliteDatabaseServiceImpl();
        CrQuestionSetting cache = CrQuestionSetting.getCrQuestionSettingFromCache();
        Connection connection = databaseService.getConnection(cache.getDbUrl(),cache.getDbUsername(),cache.getDbPwd());
        CrSqliteQuestionDO oldQuestion = getBean(connection, DmlConstants.QUERY_BY_ID, id);
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
    public Pair<Boolean, List<CrQuestion>> queryQuestion(CrQuestionQueryRequest request) {
        Set<String> stateSet = request.getStateSet();
        IDatabaseService database = SingletonBeanFactory.getSqliteDatabaseServiceImpl();
        CrQuestionSetting cache = CrQuestionSetting.getCrQuestionSettingFromCache();
        Connection connection = database.getConnection(cache.getDbUrl(),cache.getDbUsername(),cache.getDbPwd());
        try {
            List<CrSqliteQuestionDO> questionDoList = queryList(connection, DmlConstants.QUERY_SQL, new ArrayList<>(request.getProjectNameSet()).get(0));
            List<CrQuestion> questionList = toCrQuestionList(questionDoList);
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
        }
    }

    private static CrQuestion toCrQuestion(CrSqliteQuestionDO questionDO) {
        if (questionDO == null) {
            return null;
        }
        CrQuestion question = new CrQuestion();
        BeanUtils.copyProperties(questionDO, question);
        // 类型不同无法拷贝
        question.setId(Long.valueOf(questionDO.getId()));
        return question;
    }

    private static List<CrQuestion> toCrQuestionList(List<CrSqliteQuestionDO> doList) {
        List<CrQuestion> questionList = new ArrayList<>();
        if (doList == null) {
            return new ArrayList<>(0);
        }
        for (CrSqliteQuestionDO questionDO : doList) {
            questionList.add(toCrQuestion(questionDO));
        }
        return questionList;
    }
}