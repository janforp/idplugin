package com.janita.idplugin.woodpecker;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.janita.idplugin.common.Pair;
import com.janita.idplugin.common.entity.CrDeveloper;
import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.common.enums.CrDataStorageEnum;
import com.janita.idplugin.common.enums.CrQuestionState;
import com.janita.idplugin.idea.base.domain.SelectFileInfo;
import com.janita.idplugin.idea.base.util.CommonUtils;
import com.janita.idplugin.idea.base.util.CompatibleUtils;
import com.janita.idplugin.idea.base.util.GitUtils;
import com.janita.idplugin.service.crdeveloper.ICrDeveloperService;
import com.janita.idplugin.service.crdeveloper.factory.CrDeveloperServiceFactory;
import com.janita.idplugin.woodpecker.dialog.CrQuestionEditDialog;
import com.janita.idplugin.woodpecker.dialog.CrQuestionSettingDialog;
import com.janita.idplugin.woodpecker.setting.CrQuestionSettingUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrCreateCrQuestionAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        boolean clickOk = CrQuestionSettingDialog.checkStorageAndReturnIfClickOk();
        if (!clickOk) {
            // 用户点击了取消
            return;
        }
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        CrQuestion question = newQuestion(e);
        CrDataStorageEnum storageEnum = CrQuestionSettingUtils.getStorageWayFromCache();
        ICrDeveloperService developerService = CrDeveloperServiceFactory.getICrDeveloperService(storageEnum);
        Pair<Boolean, List<CrDeveloper>> pair = developerService.queryAssignName(CrQuestionSettingUtils.getCrQuestionSettingFromCache(), question.getProjectName());
        if (!pair.getLeft()) {
            CommonUtils.showNotification("CRHelper数据库配置不正确", MessageType.ERROR);
            return;
        }
        CrQuestionEditDialog crQuestionEditDialog = new CrQuestionEditDialog(project, question, pair.getRight(), true, null);
        crQuestionEditDialog.open();
    }

    /**
     * 通过复写Action 的 update 来控制Action是否可见
     *
     * @param e 事件
     */
    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        boolean selection = CommonUtils.hasSelectAnyText(e);
        // 用来设置该Action可用并且可见
        // e.getPresentation().setEnabledAndVisible(selection);
        // 用来设置该Action是否可用
        e.getPresentation().setEnabled(selection);
    }

    private static CrQuestion newQuestion(AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        VirtualFile virtualFile = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        SelectFileInfo holder = CommonUtils.getSelectFileInfo(e);

        CrQuestion question = new CrQuestion();
        question.setProjectName(CompatibleUtils.getProjectNameFromGitFirstThenFromLocal(project, virtualFile));
        question.setFilePath(holder.getFilePath());
        question.setFileName(holder.getFileName());
        question.setLanguage(holder.getLanguage());
        question.setState(CrQuestionState.UNSOLVED.getDesc());
        question.setAssignFrom(GitUtils.getGitUserName(project));
        question.setQuestionCode(holder.getSelectedText());
        question.setSuggestCode(holder.getSelectedText());
        question.setCreateGitBranchName(CompatibleUtils.getBranchNameFromGitFirstThenFromLocal(project, virtualFile));

        question.setOffsetStart(holder.getOffsetStart());
        question.setOffsetEnd(holder.getOffsetEnd());
        return question;
    }
}