package com.janita.idplugin.service;

import com.janita.idplugin.dao.DAO;
import lombok.Data;

/**
 * Service
 *
 * @author zhucj
 * @since 20220324
 */
@Data
public class Service {

    private String service = "service";

    private DAO dao = new DAO();
}
