package com.janita.idplugin.service.crquestion.domain;

import com.janita.idplugin.common.entity.CrQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * CrQuestionCreate
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@AllArgsConstructor
public class CrQuestionCreate {

    private Integer editIndex;

    private boolean sendMsg;

    private List<String> phoneList;

    private CrQuestion question;
}
