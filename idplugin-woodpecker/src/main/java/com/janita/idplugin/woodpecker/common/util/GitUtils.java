package com.janita.idplugin.woodpecker.common.util;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.VcsRoot;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcs.log.VcsUser;
import com.janita.idplugin.woodpecker.common.exception.PluginException;
import git4idea.GitUserRegistry;
import git4idea.GitUtil;
import git4idea.branch.GitBranchUtil;
import git4idea.repo.GitRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * GitUtils
 *
 * @author zhucj
 * @see GitUtil 插件自带的工具
 * @since 20220324
 */
@SuppressWarnings("unused")
public class GitUtils {

    public static String getBranchNameOrThrow(Project project, VirtualFile file) throws PluginException {
        GitRepository currentRepository = GitBranchUtil.getRepositoryOrGuess(project, file);
        if (currentRepository == null || currentRepository.getCurrentBranch() == null) {
            throw new PluginException("Can not get git branch name");
        }
        return currentRepository.getCurrentBranch().getName();
    }

    public static String getBranchNameOrReturnNull(Project project, String fullPath, String fileName) {
        VirtualFile virtualFile = CommonUtils.getByPathThenName(project, fullPath, fileName);
        try {
            return getBranchNameOrThrow(project, virtualFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getGitUserName(Project project) {
        GitUserRegistry instance = GitUserRegistry.getInstance(project);
        VcsUser user = instance.getOrReadUser(project.getBaseDir());
        return user == null ? null : user.getName();
    }

    public static Set<Repository> getAllRepositoryInProject(Project project) {
        VcsRepositoryManager vcsRepositoryManager = VcsRepositoryManager.getInstance(project);
        Collection<Repository> repositories = vcsRepositoryManager.getRepositories();
        return new HashSet<>(repositories);
    }

    public static Set<GitRepository> getAllGitRepositoryInProject(Project project) {
        Collection<GitRepository> repositories = GitUtil.getRepositories(project);
        return new HashSet<>(repositories);
    }

    public static Repository getRepository(Project project, VirtualFile file) {
        VcsRepositoryManager vcsRepositoryManager = VcsRepositoryManager.getInstance(project);
        ProjectLevelVcsManager projectLevelVcsManager = ProjectLevelVcsManager.getInstance(project);
        VcsRoot vcsRoot = projectLevelVcsManager.getVcsRootObjectFor(file);
        if (vcsRoot == null) {
            return null;
        }
        return vcsRepositoryManager.getRepositoryForRootQuick(vcsRoot.getPath());
    }

    public static GitRepository getGitRepository(Project project, VirtualFile file) throws VcsException {
        return GitUtil.getRepositoryForFile(project, file);
    }

    public static GitUserRegistry getGitUserRegistry(Project project) {
        return GitUserRegistry.getInstance(project);
    }

    public static String getProjectNameInGitOrThrow(Project project, VirtualFile file) throws PluginException {
        String projectName;
        try {
            projectName = GitUtil.getRepositoryForFile(project, file).getRoot().getName();
        } catch (VcsException e) {
            throw new PluginException("Can not get git project name");
        }
        return projectName;
    }

    public static Set<String> getAllProjectNameFromGit(Project project) {
        Set<Repository> repositorySet = GitUtils.getAllRepositoryInProject(project);
        Set<String> projectNameSet = new HashSet<>();
        for (Repository repository : repositorySet) {
            projectNameSet.add(repository.getRoot().getName());
        }
        return projectNameSet;
    }

    public static VcsUser getVcsUser(Project project) {

        GitUserRegistry instance = GitUserRegistry.getInstance(project);
        return instance.getOrReadUser(project.getBaseDir());
    }

    public static String getRepositoryNameOfFile(Project project, VirtualFile file) {
        VcsRepositoryManager vcsRepositoryManager = VcsRepositoryManager.getInstance(project);
        ProjectLevelVcsManager projectLevelVcsManager = ProjectLevelVcsManager.getInstance(project);
        VcsRoot vcsRoot = projectLevelVcsManager.getVcsRootObjectFor(file);
        if (vcsRoot == null) {
            return null;
        }
        Repository repository = vcsRepositoryManager.getRepositoryForRootQuick(vcsRoot.getPath());
        if (repository == null) {
            return null;
        }
        return repository.getRoot().getName();
    }
}