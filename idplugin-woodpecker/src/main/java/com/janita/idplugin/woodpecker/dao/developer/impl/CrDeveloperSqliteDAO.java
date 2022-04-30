package com.janita.idplugin.woodpecker.dao.developer.impl;

import com.janita.idplugin.woodpecker.common.constant.DmlConstants;
import com.janita.idplugin.remote.api.Pair;
import com.janita.idplugin.woodpecker.common.util.SingletonBeanFactory;
import com.janita.idplugin.woodpecker.dao.BaseDAO;
import com.janita.idplugin.woodpecker.dao.developer.ICrDeveloperDAO;
import com.janita.idplugin.woodpecker.dao.question.ICrQuestionDAO;
import com.janita.idplugin.remote.db.IDatabaseService;
import com.janita.idplugin.woodpecker.domain.CrDeveloper;
import com.janita.idplugin.woodpecker.domain.CrDeveloperQueryRequest;
import com.janita.idplugin.woodpecker.domain.CrDeveloperSaveRequest;
import com.janita.idplugin.woodpecker.domain.CrQuestion;
import com.janita.idplugin.woodpecker.domain.CrQuestionQueryRequest;
import com.janita.idplugin.woodpecker.setting.CrQuestionSetting;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CrDeveloper
 *
 * @author zhucj
 * @since 20220324
 */
public class CrDeveloperSqliteDAO extends BaseDAO<CrDeveloper> implements ICrDeveloperDAO {

    private CrDeveloperSqliteDAO() {
    }

    @Override
    public boolean save(CrDeveloperSaveRequest request) {
        IDatabaseService databaseService = SingletonBeanFactory.getSqliteDatabaseServiceImpl();
        CrQuestionSetting cache = CrQuestionSetting.getCrQuestionSettingFromCache();
        Connection connection = databaseService.getConnection(cache.getDbUrl(),cache.getDbUsername(),cache.getDbPwd());

        String name = request.getName();
        String email = request.getEmail();
        String phone = request.getPhone();
        CrDeveloper oldEntity = getBean(connection, DmlConstants.GET_DEVELOPER_BY_NAME, name);
        if (oldEntity == null) {
            CrDeveloper newEntity = new CrDeveloper();
            newEntity.setName(name);
            newEntity.setEmail(email);
            newEntity.setPhone(phone);
            return update(connection, DmlConstants.INSERT_DEVELOPER, newEntity.getName(), newEntity.getEmail(), newEntity.getPhone());
        }
        String newPhone = StringUtils.isNotBlank(phone) ? phone : oldEntity.getPhone();
        String newEmail = StringUtils.isNotBlank(email) ? email : oldEntity.getEmail();
        return update(connection, DmlConstants.UPDATE_DEVELOPER_BY_NAME, newEmail, newPhone, name);
    }

    @Override
    @SuppressWarnings("all")
    public Pair<Boolean, List<CrDeveloper>> queryDeveloper(CrDeveloperQueryRequest request) {
        Set<String> projectNameSet = request.getProjectNameSet();
        CrQuestionQueryRequest questionQueryRequest = new CrQuestionQueryRequest();
        questionQueryRequest.setProjectNameSet(projectNameSet);
        ICrQuestionDAO crQuestionMySqlDAO = SingletonBeanFactory.getCrQuestionSqliteDAO();
        Pair<Boolean, List<CrQuestion>> questionPair = crQuestionMySqlDAO.queryQuestion(questionQueryRequest);
        if (!questionPair.getLeft()) {
            return Pair.of(false, new ArrayList<>(0));
        }
        List<CrQuestion> questionList = questionPair.getRight();
        if (CollectionUtils.isEmpty(questionList)) {
            return Pair.of(true, new ArrayList<>(0));
        }

        IDatabaseService databaseService = SingletonBeanFactory.getSqliteDatabaseServiceImpl();
        CrQuestionSetting cache = CrQuestionSetting.getCrQuestionSettingFromCache();
        Connection connection = databaseService.getConnection(cache.getDbUrl(),cache.getDbUsername(),cache.getDbPwd());

        Set<String> collect = questionList.stream().map(CrQuestion::getAssignTo).collect(Collectors.toSet());
        String[] names = collect.toArray(new String[0]);
        List<String> whList = new ArrayList<>(names.length);
        for (String name : names) {
            whList.add("?");
        }
        String param = StringUtils.join(whList, ",");
        String sql = DmlConstants.QUERY_DEVELOPER_IN_NAME.replace("$", param);
        List<CrDeveloper> developerList = queryListByArray(connection, sql, names);
        return Pair.of(true, developerList);
    }
}
