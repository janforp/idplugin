package com.janita.idplugin.idea.base.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 兼容工具
 *
 * @author zhucj
 * @since 20220324
 */
public class CompatibleUtils {

    public static String getProjectNameFromGitFirstThenFromLocal(Project project, VirtualFile file) {
        String projectName = GitUtils.getRepositoryNameOfFile(project, file);
        if (StringUtils.isBlank(projectName)) {
            projectName = getProjectNameInLocalLocal(project, file);
        }
        return projectName;
    }

    public static String getBranchNameFromGitFirstThenFromLocal(Project project, VirtualFile file) {
        String branchName = "";
        try {
            branchName = GitUtils.getBranchNameOrThrow(project, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(branchName)) {
            branchName = "local";
        }
        return branchName;
    }

    private static String getProjectNameInLocalLocal(Project project, VirtualFile file) {
        return project.getName();
    }

    public static Set<String> getAllProjectNameFromGitFirstThenLocal(Project project) {
        Set<String> projectNameFromGit = GitUtils.getAllProjectNameFromGit(project);
        if (CollectionUtils.isNotEmpty(projectNameFromGit)) {
            return projectNameFromGit;
        }
        return getAllProjectNameFromLocal(project);
    }

    private static Set<String> getAllProjectNameFromLocal(Project project) {
        return new HashSet<>(Collections.singletonList(project.getName()));
    }
}
