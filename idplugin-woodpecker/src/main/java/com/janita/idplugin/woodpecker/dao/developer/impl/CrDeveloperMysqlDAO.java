package com.janita.idplugin.woodpecker.dao.developer.impl;

import com.janita.idplugin.remote.constant.DmlConstants;
import com.janita.idplugin.remote.api.Pair;
import com.janita.idplugin.woodpecker.common.util.SingletonBeanFactory;
import com.janita.idplugin.dao.BaseDAO;
import com.janita.idplugin.woodpecker.dao.developer.ICrDeveloperDAO;
import com.janita.idplugin.dao.crquestion.ICrQuestionDAO;
import com.janita.idplugin.common.IDatabaseService;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.request.CrDeveloperQueryRequest;
import com.janita.idplugin.common.request.CrDeveloperSaveRequest;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.request.CrQuestionQueryRequest;
import com.janita.idplugin.woodpecker.setting.CrQuestionSetting;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CrDeveloper
 *
 * @author zhucj
 * @since 20220324
 */
public class CrDeveloperMysqlDAO extends BaseDAO<CrDeveloper> implements ICrDeveloperDAO {

    private CrDeveloperMysqlDAO() {
    }

    @Override
    public boolean save(CrDeveloperSaveRequest request) {
        IDatabaseService databaseService = SingletonBeanFactory.getMySqlDatabaseServiceImpl();
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
    public Pair<Boolean, List<CrDeveloper>> queryDeveloper(CrDeveloperQueryRequest request) {
        Set<String> projectNameSet = request.getProjectNameSet();
        CrQuestionQueryRequest questionQueryRequest = new CrQuestionQueryRequest();
        questionQueryRequest.setProjectNameSet(projectNameSet);
        ICrQuestionDAO crQuestionMySqlDAO = SingletonBeanFactory.getCrQuestionMySqlDAO();
        Pair<Boolean, List<CrQuestion>> questionPair = crQuestionMySqlDAO.queryQuestion(questionQueryRequest);
        if (!questionPair.getLeft()) {
            return Pair.of(false, null);
        }
        List<CrQuestion> questionList = questionPair.getRight();
        return Pair.of(true, questionList.stream().map(question -> new CrDeveloper(question.getAssignTo())).collect(Collectors.toList()));
    }
}
