package com.janita.idplugin.woodpecker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * CrDeveloperQueryRequest
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrDeveloperQueryRequest {

    private Set<String> projectNameSet;

    private Set<String> nameSet;

    private Set<String> emailSet;

    private Set<String> phoneSet;
}