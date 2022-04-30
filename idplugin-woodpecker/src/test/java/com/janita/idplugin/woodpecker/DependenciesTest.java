package com.janita.idplugin.woodpecker;

import com.janita.idplugin.service.Service;
import org.junit.Test;

/**
 * DepenceTest
 *
 * @author zhucj
 * @since 20220324
 */
public class DependenciesTest {

    @Test
    public void testDependencies() {
        Service service = new Service();
        System.out.println(service);
    }
}
