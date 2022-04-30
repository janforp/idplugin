package com.janita.idplugin.service.wechat.factory;

import com.janita.idplugin.service.wechat.IWeChatService;
import com.janita.idplugin.service.wechat.impl.WeChatServiceImpl;

/**
 * WeChatServiceFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class WeChatServiceFactory {

    public static IWeChatService getWeChatService() {
        return WeChatServiceImpl.getINSTANCE();
    }
}
