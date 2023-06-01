package com.qqdd.common.core.domain.model;

/**
 * @auther jxy
 * @date 2023/5/17
 * @apiNote
 */
public class WxLoginBody {
    /**
     * 暂时登陆凭据 code 只能运用一次
     */
    private String code;
    /**
     * 偏移量
     */
    private String encryptedIv;
    /**
     * 加密数据
     */
    private String encryptedData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncryptedIv() {
        return encryptedIv;
    }

    public void setEncryptedIv(String encryptedIv) {
        this.encryptedIv = encryptedIv;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }
}
