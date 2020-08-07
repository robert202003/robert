package org.robert.gateway.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.robert.core.util.DateUtils;
import org.robert.core.util.EncryptUtils;
import org.robert.core.util.RandomValueUtils;
import org.robert.gateway.constant.CommonConstants;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 验签工具类
 */
@Slf4j
public class SignatureUtils {

    /**
     * 5分钟有效期
     */
    private static final long MAX_EXPIRE = 30 * 24 * 60 * 60;
    private static final int RANDOM_LENGTH = 16;


    public static void main(String[] args) throws Exception {
        String clientSecret = "000000";
        //参数签名算法测试例子
        HashMap<String, String> signMap = new HashMap<String, String>();
        signMap.put("app_id", "1");

        signMap.put("sign_type", SignType.SHA256.name());
        signMap.put("timestamp", DateUtils.getCurrentTimestampStr());
        signMap.put("nonce_str", RandomValueUtils.randomAlphanumeric(RANDOM_LENGTH));
        String sign = SignatureUtils.getSign(signMap, clientSecret);
        System.out.println("签名结果:" + sign);
        signMap.put("sign", sign);
        System.out.println("签名参数:" + JSONObject.toJSONString(signMap));
        System.out.println(SignatureUtils.validateSign(signMap, clientSecret));
    }

    /**
     * 验证参数
     *
     * @param paramsMap
     * @throws Exception
     */
    public static void validateParams(Map<String, String> paramsMap) throws Exception {
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_APP_ID_KEY), "签名验证失败:app_id不能为空");
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_NONCE_KEY), "签名验证失败:nonce_str不能为空");
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_TIMESTAMP_KEY), "签名验证失败:timestamp不能为空");
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_SIGN_TYPE_KEY), "签名验证失败:sign_type不能为空");
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_SIGN_KEY), "签名验证失败:sign不能为空");
        if (!SignatureUtils.SignType.contains(paramsMap.get(CommonConstants.SIGN_SIGN_TYPE_KEY))) {
            throw new IllegalArgumentException(String.format("签名验证失败:sign_type必须为:%s,%s", SignatureUtils.SignType.MD5, SignatureUtils.SignType.SHA256));
        }
        try {
            DateUtils.parseDate(paramsMap.get(CommonConstants.SIGN_TIMESTAMP_KEY), "yyyyMMddHHmmss");
        } catch (ParseException e) {
            throw new IllegalArgumentException("签名验证失败:TIMESTAMP格式必须为:yyyyMMddHHmmss");
        }
        //判断时间戳 timestamp=201808091113
        String timestamp = paramsMap.get(CommonConstants.SIGN_TIMESTAMP_KEY);
        String timestampStr = DateUtils.date2UnixTimeStamp(timestamp, "yyyyMMddHHmmss");
        Long clientTimestamp = Long.parseLong(timestampStr);
        long currentTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        long l = currentTimestamp - clientTimestamp;
        if (l > MAX_EXPIRE) {
            throw new IllegalArgumentException("签名验证失败:TIMESTAMP已过期");
        }
    }

    /**
     * @param paramsMap     必须包含
     * @param clientSecret
     * @return
     */
    public static boolean validateSign(Map<String, String> paramsMap, String clientSecret) {
        try {
            //validateParams(paramsMap);
            String sign = paramsMap.get(CommonConstants.SIGN_SIGN_KEY);
            //重新生成签名
            String signNew = getSign(paramsMap, clientSecret);
            //判断当前签名是否正确
            if (signNew.equals(sign)) {
                return true;
            }
        } catch (Exception e) {
            log.error("validateSign error:{}", e.getMessage());
            return false;
        }
        return false;
    }


    /**
     * 得到签名
     *
     * @param paramMap     参数集合不含appSecret
     *                     必须包含appId=客户端ID
     *                     signType = SHA256|MD5 签名方式
     *                     timestamp=时间戳
     *                     nonce=随机字符串
     * @param clientSecret 验证接口的clientSecret
     * @return
     */
    public static String getSign(Map<String, String> paramMap, String clientSecret) {
        if (paramMap == null) {
            return "";
        }
        //排序
        Set<String> keySet = paramMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        String signType = paramMap.get(CommonConstants.SIGN_SIGN_TYPE_KEY);
        SignType type = null;
        if (StringUtils.isNotBlank(signType)) {
            type = SignType.valueOf(signType);
        }
        if (type == null) {
            type = SignType.MD5;
        }
        for (String k : keyArray) {
            if (k.equals(CommonConstants.SIGN_SIGN_KEY) || k.equals(CommonConstants.SIGN_SECRET_KEY)) {
                continue;
            }
            Object value = paramMap.get(k);
            if (value == null) {
                continue;
            }
            String valueStr;
            if (value instanceof String) {
                valueStr = (String) value;
            } else {
                valueStr = JSON.toJSONString(value);
            }
            if (valueStr != null && valueStr.trim().length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(valueStr.trim()).append("&");
            }

        }
        //暂时不需要个人认证
        sb.append(CommonConstants.SIGN_SECRET_KEY+"=").append(clientSecret);
        String signStr = "";
        //加密
        switch (type) {
            case MD5:
                signStr = EncryptUtils.md5Hex(sb.toString()).toUpperCase();
                break;
            case SHA256:
                signStr = EncryptUtils.sha256Hex(sb.toString()).toUpperCase();
                break;
            default:
                break;
        }
        return signStr;
    }


    public enum SignType {
        MD5,
        SHA256;

        public static boolean contains(String type) {
            for (SignType typeEnum : SignType.values()) {
                if (typeEnum.name().equals(type)) {
                    return true;
                }
            }
            return false;
        }
    }

}
