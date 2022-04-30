package com.janita.idplugin.remote.rest;

import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * MappingJackson2HttpMessageConverter
 *
 * @author zhucj
 * @since 20220324
 */
public class MappingJackson2HttpMessageConverter extends org.springframework.http.converter.json.MappingJackson2HttpMessageConverter {

    public MappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);  //加入text/html类型的支持
        setSupportedMediaTypes(mediaTypes);// tag6
    }
}