package com.qqdd.web.controller.system;


import com.alibaba.fastjson2.JSONObject;
import com.qqdd.common.constant.Constants;
import com.qqdd.common.core.domain.AjaxResult;
import com.qqdd.common.core.domain.model.LoginUser;
import com.qqdd.common.core.domain.model.WxLoginBody;
import com.qqdd.common.utils.sign.Base64;
import com.qqdd.framework.config.WxAppConfig;
import com.qqdd.framework.web.service.MobileLoginService;
import com.qqdd.framework.web.service.TokenService;
import com.qqdd.system.domain.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Map;


/**
 * 登录控制器
 *
 * @author SK
 * @since 2018/6/13
 */
@RestController
@RequestMapping("/mobile/login")
public class LoginController {

    @Autowired(required = false)
    private TokenService tokenService;

    @Autowired(required = false)
    private MobileLoginService loginService;

    @Autowired
    private WxAppConfig wxAppConfig;


    /**
     * 会员登录
     *
     * @return -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    @PostMapping("/loginByPassword")
    @ResponseBody
    public AjaxResult loginByPassword(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phoneNo = request.getParameter("phoneNo");
        String validCode = request.getParameter("validCode");
        String loginType = request.getParameter("loginType");
        // 登录结果
        LoginParams loginParams = new LoginParams();
        loginParams.setUsername(username);
        loginParams.setPassword(password);
        loginParams.setPhoneNo(phoneNo);
        loginParams.setValidCode(validCode);
        loginParams.setLoginType(loginType);
        return loginService.login(loginParams);
    }

    /**
     * 发送验证码
     */
    @PostMapping("/sendCode")
    @ResponseBody
    public AjaxResult sendRegisterCode(HttpServletRequest request) {
        String phoneNo = request.getParameter("phoneNo");
        String validCodeType = request.getParameter("validCodeType");
        // 登录结果
        LoginParams loginParams = new LoginParams();
        loginParams.setPhoneNo(phoneNo);
        loginParams.setValidCodeType(validCodeType);
        return loginService.sendCode(loginParams);
    }

    @GetMapping("/logout")
    @ResponseBody
    public AjaxResult logout(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (null != loginUser) {
            tokenService.delLoginUser(loginUser.getToken());
        }
        return AjaxResult.success("退出成功！");
    }

    @PostMapping("/wxLogin")
    public AjaxResult wxLogin(@RequestParam Map<String, Object> params) {
        String code = (String) params.get("code");
        //秘钥
        String encryptedIv = (String) params.get("encryptedData");
        //加密数据
        String encryptedData = (String) params.get("encryptedIv");
        RestTemplate restTemplate = new RestTemplate();
        //向微信服务器发送恳求获取用户信息
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxAppConfig.getAppId() + "&secret=" + wxAppConfig.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        String res = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        //获取session_key和openid
        String sessionKey = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");
//        //解密
//        String decryptResult = "";
//        try {
//            //假如没有绑定微信敞开渠道，解析结果是没有unionid的。
//            decryptResult = decrypt(sessionKey, encryptedIv, encryptedData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return AjaxResult.error("微信登录失利！");
//        }
//        if (StringUtils.hasText(decryptResult)) {
//            //假如解析成功,获取token
//            String token = loginService.wxLogin(decryptResult);
//            AjaxResult ajax = AjaxResult.success();
//            ajax.put(Constants.TOKEN, token);
//            return ajax;
//        } else {
//            return AjaxResult.error("微信登录失利！");
//        }
        if (openid != null) {
            String username = loginService.wxLogin(openid);
            AjaxResult ajaxResult = AjaxResult.success();
            ajaxResult.put("username", username);
            return ajaxResult;
        }
        return AjaxResult.error("微信登录失败");
    }

    /**
     * AES解密
     */
    private String decrypt(String sessionKey, String encryptedIv, String encryptedData) throws Exception {
        // 转化为字节数组
        byte[] key = Base64.decode(sessionKey);
        byte[] iv = Base64.decode(encryptedIv);
        byte[] encData = Base64.decode(encryptedData);
        // 假如密钥缺乏16位，那么就补足
        int base = 16;
        if (key.length % base != 0) {
            int groups = key.length / base + (key.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }
        // 假如初始向量缺乏16位，也补足
        if (iv.length % base != 0) {
            int groups = iv.length / base + (iv.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(iv, 0, temp, 0, iv.length);
            iv = temp;
        }
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        String resultStr = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            resultStr = new String(cipher.doFinal(encData), "UTF-8");
        } catch (Exception e) {
            //            logger.info("解析错误");
            e.printStackTrace();
        }
        // 解析加密后的字符串
        return resultStr;
    }

}
