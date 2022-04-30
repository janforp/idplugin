package com.janita.idplugin.idea.base.util;

import com.intellij.ide.util.PropertiesComponent;
import lombok.experimental.UtilityClass;

/**
 * 单例
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class SingletonBeanFactory {

    public static PropertiesComponent getPropertiesComponent() {
        return PropertiesComponent.getInstance();
    }
}