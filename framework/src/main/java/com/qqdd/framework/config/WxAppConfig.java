package com.qqdd.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auther jxy
 * @date 2023/5/17
 * @apiNote
 */
@Configuration
public class WxAppConfig {

    @Value("${wx-app.appId}")
    String AppId;
    @Value("${wx-app.appSecret}")
    String AppSecret;

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String AppId) {
        this.AppId = AppId;
    }

    public String getAppSecret() {
        return AppSecret;
    }

    public void setAppSecret(String appSecret) {
        AppSecret = appSecret;
    }
}
