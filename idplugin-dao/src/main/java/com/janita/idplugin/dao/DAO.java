package com.janita.idplugin.dao;

import com.janita.idplugin.remote.Remote;
import lombok.Data;

/**
 * DAO
 *
 * @author zhucj
 * @since 20220324
 */
@Data
public class DAO {

    private String dao = "dao";

    private Remote remote = new Remote();
}
