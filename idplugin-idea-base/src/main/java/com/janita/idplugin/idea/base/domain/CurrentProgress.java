package com.janita.idplugin.idea.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * CurrentProgress
 *
 * @author zhucj
 * @since 20220324
 */
@Getter
@Setter
public class CurrentProgress {

    private long already;

    private long total;
}
