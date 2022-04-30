package com.janita.idplugin.woodpecker.common.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.ApplicationManager;
import com.janita.idplugin.woodpecker.service.CrQuestionService;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class SingletonBeanFactory {

    private static final Map<String, Set<Object>> BEAN_MAP = new ConcurrentHashMap<>();

    public static CrQuestionService getCrQuestionService() {
        CrQuestionService service = ApplicationManager.getApplication().getService(CrQuestionService.class);
        doAfterGetBean("CrQuestionService", service);
        return service;
    }

    public static PropertiesComponent getPropertiesComponent() {
        PropertiesComponent component = PropertiesComponent.getInstance();
        doAfterGetBean("PropertiesComponent", component);
        return component;
    }

    @SuppressWarnings("all")
    private static void doAfterGetBean(String beanName, Object bean) {
        Set<Object> objectSet = BEAN_MAP.get(beanName);
        if (objectSet == null) {
            objectSet = new HashSet<>();
            BEAN_MAP.put(beanName, objectSet);
        }
        objectSet.add(bean);
    }
}
