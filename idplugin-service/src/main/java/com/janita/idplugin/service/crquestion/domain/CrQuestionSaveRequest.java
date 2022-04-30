package com.janita.idplugin.service.crquestion.domain;

import com.janita.idplugin.common.entity.CrQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * CrQuestionCreate
 *
 * @author zhucj
 * @since 202203242
 */
@Data
@AllArgsConstructor
public class CrQuestionSaveRequest {

    private Integer editIndex;

    private boolean sendMsg;

    private List<String> phoneList;

    private CrQuestion question;
}
