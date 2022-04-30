package com.janita.idplugin.woodpecker.util;

import com.janita.idplugin.common.entity.CrQuestion;
import com.janita.idplugin.woodpecker.export.vo.CrQuestionExportVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionUtils {

    public static List<CrQuestionExportVO> processBeforeExport(List<CrQuestion> crQuestionList) {
        if (crQuestionList == null) {
            return new ArrayList<>(0);
        }

        List<CrQuestionExportVO> questionList = new ArrayList<>(crQuestionList.size());
        for (CrQuestion question : crQuestionList) {
            CrQuestionExportVO clone = new CrQuestionExportVO();
            clone.setProjectName(StringUtils.defaultIfBlank(question.getProjectName(), "无"));
            clone.setCreateGitBranchName(StringUtils.defaultIfBlank(question.getCreateGitBranchName(), "无"));
            clone.setFileName(StringUtils.defaultIfBlank(question.getFileName(), "无"));
            clone.setType(StringUtils.defaultIfBlank(question.getType(), "无"));
            clone.setAssignTo(StringUtils.defaultIfBlank(question.getAssignTo(), "无"));
            clone.setLevel(StringUtils.defaultIfBlank(question.getLevel(), "无"));
            clone.setState(StringUtils.defaultIfBlank(question.getState(), "无"));
            clone.setQuestionCode(setCodeMark(StringUtils.defaultIfBlank(question.getQuestionCode(), "无")));
            clone.setBetterCode(setCodeMark(StringUtils.defaultIfBlank(question.getSuggestCode(), "无")));
            clone.setDesc(setCodeMark(StringUtils.defaultIfBlank(question.getDescription(), "无")));
            questionList.add(clone);
        }
        return questionList;
    }

    private static String setCodeMark(String text) {
        if (text == null || text.length() == 0) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        String[] split = text.split("\n");
        for (String str : split) {
            if (str.trim().length() == 0) {
                continue;
            }
            str = ">>" + str + "\n";
            result.append(str);
        }
        return result.toString().trim();
    }

    public static boolean isChanged(CrQuestion rawQuestion, CrQuestion question) {
        if (question == null || rawQuestion == null) {
            return false;
        }
        return !Objects.equals(question.getType(), rawQuestion.getType()) ||
                !Objects.equals(question.getLevel(), rawQuestion.getLevel()) ||
                !Objects.equals(question.getState(), rawQuestion.getState()) ||
                !Objects.equals(question.getAssignTo(), rawQuestion.getAssignTo()) ||
                !Objects.equals(question.getSuggestCode(), rawQuestion.getSuggestCode()) ||
                !Objects.equals(question.getDescription(), rawQuestion.getDescription()) ||
                !Objects.equals(question.getSolveGitBranchName(), rawQuestion.getSolveGitBranchName());
    }
}